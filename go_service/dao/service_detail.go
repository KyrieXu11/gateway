package dao

import (
	"fmt"
	"gateway/common/utils"
)

type ServiceHttp struct {
	Id            int
	ServiceId     int64
	RuleType      byte
	Rule          string
	NeedHttps     byte
	NeedStripUri  byte
	NeedWebsocket byte
	UrlRewrite    string
}

func (p ServiceHttp) TableName() string {
	return "gateway_service_http_rule"
}

type ServiceTcp struct {
	Id        int64
	ServiceId int64
	Port      int
}

func (s ServiceTcp) TableName() string {
	return "gateway_service_tcp_rule"
}

type ServiceHttpDao struct {
}

type ServiceTcpDao struct {
}

func (p *ServiceHttpDao) GetHttpDetail(serviceId int64) (*ServiceHttp, error) {
	var service_http ServiceHttp
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&service_http).Error; err != nil {
		return nil, fmt.Errorf("服务类型为[http]找不到service_id为%d的记录", serviceId)
	}
	return &service_http, nil
}

func (p *ServiceTcpDao) GetTcpDetail(serviceId int64) (*ServiceTcp, error) {
	var service_tcp ServiceTcp
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&service_tcp).Error; err != nil {
		return nil, fmt.Errorf("服务类型为[tcp]找不到service_id为%d的记录", serviceId)
	}
	return &service_tcp, nil
}
