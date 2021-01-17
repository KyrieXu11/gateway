package service

import (
	"fmt"
	"gateway/dao"
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
