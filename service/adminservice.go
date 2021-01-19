package service

import (
	"fmt"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/dto"
)

func GetAdminByUsername(username string) (*dao.Admin, error) {
	admin := dao.FindAdminByUserName(username)
	if admin == nil {
		return nil, fmt.Errorf("未找到用户")
	}
	return admin, nil
}

func CheckPassword(password string, admin *dao.Admin) bool {
	return admin.CheckPassword(password)
}

// RegisterAdmin
func RegisterAdmin(adminDto *dto.AdminDto) bool{
	password := adminDto.Password
	username := adminDto.Username
	password = utils.GetSaltyPassword(utils.GenerateSalt(5), password)
	return dao.RegisterAdmin(username,password)
}
