package dao

import (
	"fmt"
	"gateway/common/utils"
)

type HttpRule struct {
	Id             int
	ServiceId      int64
	RuleType       byte
	Rule           string
	NeedHttps      byte
	NeedStripUri   byte
	NeedWebsocket  byte
	UrlRewrite     string
	HeaderTransfor string
}

func (p HttpRule) TableName() string {
	return "gateway_service_http_rule"
}

type TcpRule struct {
	Id        int64
	ServiceId int64
	Port      int
}

func (s TcpRule) TableName() string {
	return "gateway_service_tcp_rule"
}

type GrpcRule struct {
	ID             int64  `json:"id" gorm:"primary_key"`
	ServiceID      int64  `json:"service_id" gorm:"column:service_id" description:"服务id"`
	Port           int    `json:"port" gorm:"column:port" description:"端口	"`
	HeaderTransfor string `json:"header_transfor" gorm:"column:header_transfor" description:"header转换支持增加(add)、删除(del)、修改(edit) 格式: add headname headvalue"`
}

func (t *GrpcRule) TableName() string {
	return "gateway_service_grpc_rule"
}

type HttpRuleDao struct{}

type TcpRuleDao struct{}

type GrpcRuleDao struct{}

func (p *HttpRuleDao) GetHttpRule(serviceId int64) (*HttpRule, error) {
	var httpRule HttpRule
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&httpRule).Error; err != nil {
		return nil, fmt.Errorf("服务类型为[http]找不到service_id为%d的记录", serviceId)
	}
	return &httpRule, nil
}

func (p *TcpRuleDao) GetTcpRule(serviceId int64) (*TcpRule, error) {
	var tcpRule TcpRule
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&tcpRule).Error; err != nil {
		return nil, fmt.Errorf("服务类型为[tcp]找不到service_id为%d的记录", serviceId)
	}
	return &tcpRule, nil
}

func (p *GrpcRuleDao) GetgRpcRule(serviceId int64) (*GrpcRule, error) {
	var gRpcRule GrpcRule
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&gRpcRule).Error; err != nil {
		return nil, fmt.Errorf("服务类型为[grpc]找不到service_id为%d的记录", serviceId)
	}
	return &gRpcRule, nil
}
