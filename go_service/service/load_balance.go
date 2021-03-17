package service

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/middleware/reverse_proxy/load_balance"
	"sync"
)

var LoadBalanceHandler *LoadBalanceManager

type LoadBalanceManager struct {
	LoadBalanceMap   map[string]*LoadBalanceItem
	LoadBalanceSlice []*LoadBalanceItem
	Locker           sync.RWMutex
}

type LoadBalanceItem struct {
	ServiceName  string
	ILoadBalance load_balance.LoadBalance
}

func NewLoadBalanceManager() *LoadBalanceManager {
	return &LoadBalanceManager{
		LoadBalanceMap:   map[string]*LoadBalanceItem{},
		LoadBalanceSlice: []*LoadBalanceItem{},
		Locker:           sync.RWMutex{},
	}
}

func InitLoadBalanceHandler() {
	LoadBalanceHandler = NewLoadBalanceManager()
}

func (p *LoadBalanceManager) GetLoadBalancer(s *dao.ServiceDetail) (load_balance.LoadBalance, error) {
	serviceName := s.Info.ServiceName
	for _, balance := range p.LoadBalanceSlice {
		if balance.ServiceName == serviceName {
			return balance.ILoadBalance, nil
		}
	}
	protocol := "http"
	if s.HTTPRule.NeedHttps == utils.NeedHttps {
		protocol = "https"
	}
	if s.Info.LoadType == utils.TCPLoadType || s.Info.LoadType == utils.GrpcLoadType {
		protocol = ""
	}
	weightList := s.LoadBalance.GetWeightListByModel()
	ipList := s.LoadBalance.GetIPListByModel()
	ipConf := map[string]string{}
	for idx, ip := range ipList {
		ipConf[ip] = weightList[idx]
	}
	lbConf, err := load_balance.NewLoadBalanceCheckConf(fmt.Sprintf("%s://%s", protocol, "%s"), ipConf)
	if err != nil {
		log.Error(err.Error())
		return nil, err
	}
	lb := load_balance.LoadBalanceFactorWithConf(load_balance.LbType(s.LoadBalance.RoundType), lbConf)
	loadBalanceItem := &LoadBalanceItem{
		ILoadBalance: lb,
		ServiceName:  serviceName,
	}
	// save item into slice and map
	p.Locker.Lock()
	defer p.Locker.Unlock()
	p.LoadBalanceSlice = append(p.LoadBalanceSlice, loadBalanceItem)
	p.LoadBalanceMap[serviceName] = loadBalanceItem
	return lb, nil
}
