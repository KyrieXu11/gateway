package dao

import (
	"gateway/common/log"
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

func (p *ServiceHttp) TableName() string {
	return "gateway_service_http_rule"
}

type ServiceHttpDao struct {
}

func (p *ServiceHttpDao) GetServiceDetailByServiceId(serviceId int64) *ServiceHttp {
	db := utils.GetDB()
	var httpService ServiceHttp
	if err := db.Where("service_id = ?", serviceId).First(&httpService).Error; err != nil {
		log.Error(err.Error())
		return nil
	}
	return &httpService
}
