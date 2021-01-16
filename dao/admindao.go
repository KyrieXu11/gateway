package dao

import (
	"gateway/common/log"
	"gateway/common/utils"
	"time"
)

type Admin struct {
	Id       int
	Username string
	Password string
	Salt     string
	Deleted  byte `gorm:"column:is_deleted"`
	CreateAt time.Time
	UpdateAt time.Time
}

func (p *Admin) TableName() string {
	return "gateway_admin"
}

// 根据用户名查找管理员帐户
func FindAdminByUserName(username string) *Admin {
	var res *Admin
	if err := utils.DB.Where("username = ?", username).First(res).Error; err != nil {
		log.Error(err.Error())
		return nil
	}
	return res
}
