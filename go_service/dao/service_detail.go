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

type ServiceDetailDao struct {
}

type ServiceHttpDao struct {
}

type ServiceTcpDao struct {
}

// TODO: gorm not allowed to use interface in sql,must slice or struct
func (p *ServiceDetailDao) GetServiceDetailByServiceId(serviceId int64, inter Tabler) Tabler {
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&inter).Error; err != nil {
		log.Error(err.Error())
		return nil
	}
	return inter
}
