package dao

import (
	"gateway/common/log"
	"gateway/common/utils"
	"time"
)

type ServiceInfo struct {
	Id          int
	LoadType    byte      `gorm:"column:load_type"`
	ServiceName string    `gorm:"column:service_name"`
	ServiceDesc string    `gorm:"column:service_desc"`
	CreateAt    time.Time `gorm:"column:create_at"`
	UpdateAt    time.Time `gorm:"column:update_at"`
	Deleted     byte      `gorm:"column:is_delete"`
}

func (p *ServiceInfo) TableName() string {
	return "gateway_service_info"
}

type ServiceInfoDao struct {
}

func (p *ServiceInfoDao) GetServiceList(page, size int) []*ServiceInfo {
	db := utils.GetDB()
	var res []*ServiceInfo
	if err := db.Offset(page).Limit(size).Find(&res).Error; err != nil {
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
