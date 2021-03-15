package dao

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
	"time"
)

type ServiceInfo struct {
	Id          int64     `json:"id" gorm:"primary_key"`
	LoadType    int       `json:"load_type" gorm:"column:load_type"`
	ServiceName string    `json:"service_name" gorm:"column:service_name"`
	ServiceDesc string    `json:"service_desc" gorm:"column:service_desc"`
	CreateAt    time.Time `json:"create_at" gorm:"column:create_at"`
	UpdateAt    time.Time `json:"update_at" gorm:"column:update_at"`
	Deleted     int8      `json:"deleted" gorm:"column:is_delete"`
}

func (p *ServiceInfo) TableName() string {
	return "gateway_service_info"
}

type ServiceInfoDao struct{}

func (p *ServiceInfoDao) GetServiceList(page, size int) []*ServiceInfo {
	db := utils.GetDB()
	var res []*ServiceInfo
	if err := db.Where("is_delete = 0").Offset(page).Limit(size).Find(&res).Error; err != nil {
		log.Error(err.Error())
		return nil
	}
	return res
}

func (p *ServiceInfoDao) GetTotalPages(size int) int64 {
	db := utils.GetDB()
	var count int64
	var info ServiceInfo
	db.Table(info.TableName()).Count(&count)
	s := int64(size)
	if count%s != 0 {
		return count/int64(size) + 1
	}
	return count / s
}

func (p *ServiceInfoDao) FindServiceInfoById(serviceId int64) (*ServiceInfo, error) {
	var serviceInfo ServiceInfo
	db := utils.GetDB()
	if err := db.Where("id = ?", serviceId).First(&serviceInfo).Error; err != nil {
		return nil, fmt.Errorf("没找到服务id为%d的服务", serviceId)
	}
	return &serviceInfo, nil
}
