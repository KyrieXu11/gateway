package dao

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
)

type AccessControl struct {
	ID                int64  `json:"id" gorm:"primary_key"`
	ServiceID         int64  `json:"service_id" gorm:"column:service_id" description:"服务id"`
	OpenAuth          int    `json:"open_auth" gorm:"column:open_auth" description:"是否开启权限 1=开启"`
	BlackList         string `json:"black_list" gorm:"column:black_list" description:"黑名单ip	"`
	WhiteList         string `json:"white_list" gorm:"column:white_list" description:"白名单ip	"`
	WhiteHostName     string `json:"white_host_name" gorm:"column:white_host_name" description:"白名单主机	"`
	ClientIPFlowLimit int    `json:"clientip_flow_limit" gorm:"column:clientip_flow_limit" description:"客户端ip限流	"`
	ServiceFlowLimit  int    `json:"service_flow_limit" gorm:"column:service_flow_limit" description:"服务端限流	"`
}

func (t *AccessControl) TableName() string {
	return "gateway_service_access_control"
}

type AccessControlDao struct{}

func (p *AccessControlDao) FindByServiceId(serviceId int64) (*AccessControl, error) {
	var accessControl AccessControl
	db := utils.GetDB()
	if err := db.Where("service_id = ?", serviceId).First(&accessControl).Error; err != nil {
		log.Error(err.Error())
		return nil, fmt.Errorf("找不到服务id为%d的访问策略", serviceId)
	}
	return &accessControl, nil
}
