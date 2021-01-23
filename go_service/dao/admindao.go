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
	Deleted  byte `gorm:"column:is_delete"`
	CreateAt time.Time
	UpdateAt time.Time
}

func (p *Admin) TableName() string {
	return "gateway_admin"
}

func (p *Admin) CheckPassword(password string) bool {
	// 真正的密码是从数据库查询出来的
	realPass := p.Password
	// 前端传过来的密码还需要加盐
	// 然后将这个密码和真正的密码进行比对
	password = utils.GetSaltyPassword(p.Salt, password)
	return realPass == password
}

// 根据用户名查找管理员帐户
func FindAdminByUserName(username string) *Admin {
	db := utils.GetDB()
	var res = &Admin{}
	if err := db.Where("username = ?", username).First(res).Error; err != nil {
		log.Error(err.Error())
		return nil
	}
	return res
}

func RegisterAdmin(username, password, salt string) bool {
	db := utils.GetDB()
	a := Admin{
		Username: username,
		Password: password,
		Salt:     salt,
		Deleted:  0,
		CreateAt: time.Now(),
		UpdateAt: time.Now(),
	}
	res := db.NewRecord(a)
	db.Create(&a)
	log.Info("注册结果:", res)
	return res
}
