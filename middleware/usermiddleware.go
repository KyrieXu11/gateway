package middleware

import (
	"gateway/common/log"
	"gateway/common/utils"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"net/http"
	"strings"
)

// 检查是否登陆，如果没登陆的话就登录
func CheckLogin() gin.HandlerFunc {
	return func(c *gin.Context) {
		session := sessions.Default(c)
		if name, ok := session.Get(utils.SessionKeyUser).(string); !ok || name == "" {
			if CheckLoginRequest(c.Request) {
				c.Next()
				return
			}
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
	var whitList = []string{
		"/**/login",
		"/**/register",
	}
	match, isLogin := false, false
	realPath := r.URL.Path

	matcher := &utils.AntPathMatcher{
		PathSeparator: "/",
	}

	for i, s := range whitList {
		match = matcher.Match(s, realPath)
		if match {
			if i == 0 {
				isLogin = true
			}
			break
		}
	}
	if match && isLogin && strings.ToLower(r.Method) == "post" {
		return true
	}
	return match
}
