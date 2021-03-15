package service

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/dto"
	"github.com/gin-gonic/gin"
	"go.uber.org/zap"
	"math"
	"strings"
	"sync"
)

type ServiceDetailService interface{}

type ServiceDetailServiceImpl struct{}

var (
	httpRuleDao      dao.HttpRuleDao
	tcpRuleDao       dao.TcpRuleDao
	grpcRuleDao      dao.GrpcRuleDao
	loadBalanceDao   dao.LoadBalanceDao
	accessControlDao dao.AccessControlDao
)

func (p *ServiceDetailServiceImpl) GetServiceDetail(search *dto.ServiceSearch) (*dao.ServiceDetail, error) {
	res := &dao.ServiceDetail{}
	search.ServiceType = strings.ToLower(search.ServiceType)
	typeList := []string{utils.HttpServiceType, utils.TcpServiceType, utils.GrpcServiceType, utils.AllServiceType}
	if contains := utils.SliceContains(typeList, search.ServiceType); !contains {
		return nil, fmt.Errorf("检查服务类型是否正确")
	}
	serviceInfo, err := serviceInfoDao.FindServiceInfoById(search.ServiceId)
	if err != nil {
		return nil, err
	}
	res.Info = serviceInfo
	switch search.ServiceType {
	case "http":
		httpRule, err := httpRuleDao.GetHttpRule(search.ServiceId)
		if err != nil {
			log.Error(err.Error())
			return nil, err
		}
		res.HTTPRule = httpRule
		break
	case "tcp":
		tcpRule, err := tcpRuleDao.GetTcpRule(search.ServiceId)
		if err != nil {
			log.Error(err.Error())
			return nil, err
		}
		res.TCPRule = tcpRule
		break
	case "grpc":
		grpcRule, err := grpcRuleDao.GetgRpcRule(search.ServiceId)
		if err != nil {
			log.Error(err.Error())
			return nil, err
		}
		res.GRPCRule = grpcRule
		break
	case "all":
		httpRule, err := httpRuleDao.GetHttpRule(search.ServiceId)
		if err != nil {
			log.Info("no found http rule record", zap.Int64("service_id", search.ServiceId))
			httpRule = nil
		}
		res.HTTPRule = httpRule
		grpcRule, err := grpcRuleDao.GetgRpcRule(search.ServiceId)
		if err != nil {
			log.Info("no found grpc rule record", zap.Int64("service_id", search.ServiceId))
			grpcRule = nil
		}
		res.GRPCRule = grpcRule
		tcpRule, err := tcpRuleDao.GetTcpRule(search.ServiceId)
		if err != nil {
			log.Info("no found tcp rule record", zap.Int64("service_id", search.ServiceId))
			tcpRule = nil
		}
		res.TCPRule = tcpRule
		break
	}
	loadBalance, err := loadBalanceDao.FindByServiceId(search.ServiceId)
	if err != nil {
		return nil, err
	}
	accessControl, err := accessControlDao.FindByServiceId(search.ServiceId)
	if err != nil {
		return nil, err
	}
	res.LoadBalance = loadBalance
	res.AccessControl = accessControl
	return res, nil
}

var (
	ServiceManagerHandler *ServiceManager
	serviceInfoDao        dao.ServiceInfoDao
)

func init() {
	ServiceManagerHandler = NewServiceManager()
}

type ServiceManager struct {
	ServiceMap   map[string]*dao.ServiceDetail
	ServiceSlice []*dao.ServiceDetail
	Lock         sync.RWMutex
	once         sync.Once
	err          error
}

func NewServiceManager() *ServiceManager {
	return &ServiceManager{
		ServiceMap:   map[string]*dao.ServiceDetail{},
		ServiceSlice: []*dao.ServiceDetail{},
		Lock:         sync.RWMutex{},
		once:         sync.Once{},
	}
}

func (p *ServiceManager) HTTPAccessMode(c *gin.Context) (*dao.ServiceDetail, error) {
	host := c.Request.Host
	host = host[:strings.Index(host, ":")]
	path := c.Request.URL.Path
	for _, serviceItem := range p.ServiceSlice {
		if serviceItem.Info.LoadType != utils.HTTPLoadType {
			continue
		}
		if serviceItem.HTTPRule.RuleType == utils.HTTPRuleTypeDomain {
			if serviceItem.HTTPRule.Rule == host {
				return serviceItem, nil
			}
		}
		if serviceItem.HTTPRule.RuleType == utils.HTTPRuleTypePrefixURL {
			if strings.HasPrefix(path, serviceItem.HTTPRule.Rule) {
				return serviceItem, nil
			}
		}
	}
	return nil, fmt.Errorf("没有匹配到服务")
}

func (p *ServiceManager) LoadOnce() error {
	p.once.Do(func() {
		list := serviceInfoDao.GetServiceList(0, math.MaxInt32)
		s := ServiceDetailServiceImpl{}
		for _, info := range list {
			search := dto.ServiceSearch{
				ServiceId:   info.Id,
				ServiceType: utils.AllServiceType,
			}
			detail, err := s.GetServiceDetail(&search)
			if err != nil {
				log.Error(err.Error())
				p.err = err
				return
			}
			p.ServiceMap[info.ServiceName] = detail
			p.ServiceSlice = append(p.ServiceSlice, detail)
		}
	})
	return p.err
}
