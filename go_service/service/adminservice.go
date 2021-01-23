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

func (p *AdminServiceImpl) GetAdminByUsername(username string) (*dao.Admin, error) {
	admin := dao.FindAdminByUserName(username)
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
	return dao.RegisterAdmin(username, password, salt)
}

func (p *AdminServiceImpl) ChangePassword(passwordDto *dto.PasswordDto) (bool, error) {
	a := &dao.Admin{
		Username: passwordDto.Username,
	}
	res := a.CheckPassword(passwordDto.OldPass)
	if !res {
		return false, fmt.Errorf("原来的密码不对呀,再检查一下吧")
	}

	return res, nil
}
