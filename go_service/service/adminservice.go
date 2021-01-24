package service

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/dto"
)

type AdminService interface {
	GetAdminByUsername(username string) (*dao.Admin, error)
	RegisterAdmin(adminDto *dto.AdminDto) bool
	CheckPassword(password string, admin *dao.Admin) bool
	ChangePassword(passwordDto *dto.PasswordDto) (bool, error)
}

type AdminServiceImpl struct {
}

var adminDao dao.AdminDao

func (p *AdminServiceImpl) GetAdminByUsername(username string) (*dao.Admin, error) {
	admin := adminDao.FindAdminByUserName(username)
	if admin == nil {
		return nil, fmt.Errorf("未找到用户")
	}
	return admin, nil
}

func (p *AdminServiceImpl) CheckPassword(password string, admin *dao.Admin) bool {
	return admin.CheckPassword(password)
}

// RegisterAdmin
func (p *AdminServiceImpl) RegisterAdmin(adminDto *dto.AdminDto) bool {
	password := adminDto.Password
	username := adminDto.Username
	salt := utils.GenerateSalt(5)
	password = utils.GetSaltyPassword(salt, password)
	log.Infof("username: %s   password: %s   salt: %s", username, password, salt)
	return adminDao.RegisterAdmin(username, password, salt)
}

// 修改密码
func (p *AdminServiceImpl) ChangePassword(passwordDto *dto.PasswordDto) (bool, error) {
	a := adminDao.FindAdminByUserName(passwordDto.Username)
	if a == nil {
		return false, fmt.Errorf("没有登陆呀，先去登陆再来吧")
	}
	res := a.CheckPassword(passwordDto.OldPass)
	if !res {
		return false, fmt.Errorf("原来的密码不对呀,再检查一下吧")
	}
	// 需要用盐加密密码
	newPass := utils.GetSaltyPassword(a.Salt, passwordDto.NewPass)
	rows := adminDao.ChangePassword(passwordDto.Username, newPass)
	return rows != 0, nil
}
