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
	// TODO: 做登陆路径的校验
	// 介于golang里面没有类似spring中的 AntPathMatcher
	// 考虑下面两种做法
	// 1. 自己照着spring的源码写一个
	// 2. 做一个 rpc 调用
	realPath := r.URL.Path
	log.Info(realPath)
	return false
}
