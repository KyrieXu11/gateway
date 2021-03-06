package dao

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
	"strings"
)

type LoadBalance struct {
	ID            int64  `json:"id" gorm:"primary_key"`
	ServiceID     int64  `json:"service_id" gorm:"column:service_id" description:"服务id	"`
	CheckMethod   int    `json:"check_method" gorm:"column:check_method" description:"检查方法 tcpchk=检测端口是否握手成功	"`
	CheckTimeout  int    `json:"check_timeout" gorm:"column:check_timeout" description:"check超时时间	"`
	CheckInterval int    `json:"check_interval" gorm:"column:check_interval" description:"检查间隔, 单位s		"`
	RoundType     int    `json:"round_type" gorm:"column:round_type" description:"轮询方式 round/weight_round/random/ip_hash"`
	IpList        string `json:"ip_list" gorm:"column:ip_list" description:"ip列表"`
	WeightList    string `json:"weight_list" gorm:"column:weight_list" description:"权重列表"`
	ForbidList    string `json:"forbid_list" gorm:"column:forbid_list" description:"禁用ip列表"`

	UpstreamConnectTimeout int `json:"upstream_connect_timeout" gorm:"column:upstream_connect_timeout" description:"下游建立连接超时, 单位s"`
	UpstreamHeaderTimeout  int `json:"upstream_header_timeout" gorm:"column:upstream_header_timeout" description:"下游获取header超时, 单位s	"`
	UpstreamIdleTimeout    int `json:"upstream_idle_timeout" gorm:"column:upstream_idle_timeout" description:"下游链接最大空闲时间, 单位s	"`
	UpstreamMaxIdle        int `json:"upstream_max_idle" gorm:"column:upstream_max_idle" description:"下游最大空闲链接数"`
}

func (t *LoadBalance) TableName() string {
	return "gateway_service_load_balance"
}

type LoadBalanceDao struct{}

func (p *LoadBalanceDao) FindByServiceId(serviceId int64) (*LoadBalance, error) {
	var loadBalance LoadBalance
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&loadBalance).Error; err != nil {
		log.Error(err.Error())
		return nil, fmt.Errorf("找不到服务id为%d的负载均衡策略", serviceId)
	}
	return &loadBalance, nil
}

func (l *LoadBalance) GetIPListByModel() []string {
	return strings.Split(l.IpList, ",")
}

func (l *LoadBalance) GetWeightListByModel() []string {
	return strings.Split(l.WeightList, ",")
}
