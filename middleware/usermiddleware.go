package middleware

import (
	"gateway/common/log"
	"gateway/common/utils"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"net/http"
)

// 检查是否登陆，如果没登陆的话就登录
func CheckLogin() gin.HandlerFunc {
	return func(c *gin.Context) {
		if CheckLoginRequest(c.Request) {
			c.Next()
			return
		}
		session := sessions.Default(c)
		if name, ok := session.Get(utils.SessionKeyUser).(string); ok || name == "" {
			log.Info("用户未登陆")
			utils.ResponseErrorM(c, "用户未登陆")
			c.Abort()
		}
		c.Next()
	}
}

// 检查是否为登录请求的函数
func CheckLoginRequest(r *http.Request) bool {
	// 说明见 README.md
	var pattern = "/**/login"
	realPath := r.URL.Path
	command, err := utils.ExecCommand("java", "./build/AntPathMatcher.jar", pattern, realPath)
	if err != nil {
		log.Error(err.Error())
		return false
	}
	return utils.StringToBool(command)
}
