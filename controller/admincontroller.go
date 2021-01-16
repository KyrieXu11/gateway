package controller

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dto"
	"gateway/service"
	"github.com/gin-gonic/gin"
)

// 定义空的结构体让不同的控制器的函数的名称可以一样
type AdminRegistrator struct{}

// 注册控制器
func RegisterAdminController(group *gin.RouterGroup) {
	p := &AdminRegistrator{}
	group.GET("/hello", p.Hello)
	group.POST("/login", p.Login)
}

// 分组的测试接口
func (p *AdminRegistrator) Hello(c *gin.Context) {
	utils.ResponseSuccessObj(c, "admin/hello", nil)
}

// 登录接口
func (p *AdminRegistrator) Login(c *gin.Context) {
	adminDto := &dto.AdminDto{}
	if err := adminDto.ValidateAndBindParam(c); err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, "检查参数是否填写正确")
		return
	}
	admin, err := service.GetAdminByUsername(adminDto.Username)
	if err != nil {
		log.Error(err)
		utils.ResponseErrorM(c, err.Error())
		return
	}
	if res := service.CheckPassword(adminDto.Password, admin); res {
		utils.ResponseSuccessObj(c, "登陆成功！", adminDto)
	}
	utils.ResponseErrorM(c, "登陆失败")
}
