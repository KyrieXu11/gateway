package controller

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dto"
	"gateway/service"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
)

// 定义空的结构体让不同的控制器的函数的名称可以一样
type AdminRegistrator struct{}

// adminService 是admin service 层的对象
var adminService service.AdminServiceImpl

// 注册控制器
func RegisterAdminController(g *gin.RouterGroup) {
	p := &AdminRegistrator{}
	g.GET("/hello", p.Hello)
	g.POST("/login", p.Login)
	g.GET("/session/hello", p.SessionHello)
	g.GET("/logout", p.Logout)
	g.POST("/register", p.Register)
	g.PUT("/changepass", p.ChangePassword)
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
	admin, err := adminService.GetAdminByUsername(adminDto.Username)
	if err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, err.Error())
		return
	}
	if res := adminService.CheckPassword(adminDto.Password, admin); !res {
		utils.ResponseErrorM(c, "登陆失败")
		return
	}
	adminDto.Password = ""
	// 设置 session
	utils.SetSession(c)
	if err = utils.SetSessionVal(c, utils.SessionKeyUser, adminDto); err != nil {
		log.Error(err.Error())
	}
	utils.ResponseSuccessObj(c, "登陆成功！", adminDto)
}

func (p *AdminRegistrator) Logout(c *gin.Context) {
	session := sessions.Default(c)
	times := 0
	for true {
		// 在删除 session 的时候还要清除 session 种的kv
		// 给爷整吐了,草
		session.Clear()
		session.Options(sessions.Options{
			MaxAge: -1,
		})
		err := session.Save()
		if err == nil {
			break
		}
		if times == 5 {
			break
		}
		times++
	}
	if times == 5 {
		utils.ResponseErrorM(c, "注销失败,请待会再试试吧")
	} else {
		utils.ResponseSuccessObj(c, "注销成功", nil)
	}
}

func (p *AdminRegistrator) SessionHello(c *gin.Context) {
	a := &dto.AdminDto{}
	if err := utils.GetSessionVal(c, utils.SessionKeyUser, a); err != nil {
		log.Error(err.Error())
		return
	}
	utils.ResponseSuccessObj(c, "", a)
	return
}

func (p *AdminRegistrator) ChangePassword(c *gin.Context) {
	passwordDto := &dto.PasswordDto{}
	if err := passwordDto.ValidateAndBindParam(c); err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, "修改出错了")
	}
	a := &dto.AdminDto{}
	if err := utils.GetSessionVal(c, utils.SessionKeyUser, a); err != nil {
		log.Error(err.Error())
		return
	}
	passwordDto.Username = a.Username
	res, err := adminService.ChangePassword(passwordDto)
	if err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, err.Error())
		return
	}
	if res {
		utils.ResponseSuccessObj(c, "修改成功了", nil)
		return
	}
	utils.ResponseErrorM(c, "修改失败了")
}

func (p *AdminRegistrator) Register(c *gin.Context) {
	adminDto := &dto.AdminDto{}
	if err := adminDto.ValidateAndBindParam(c); err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, "检查参数是否填写正确")
		return
	}
	res := adminService.RegisterAdmin(adminDto)
	if res {
		utils.ResponseSuccessObj(c, "注册成功", nil)
	} else {
		utils.ResponseErrorM(c, "注册失败了")
	}
}
