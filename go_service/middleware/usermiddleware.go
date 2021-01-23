package middleware

import (
	"gateway/common/log"
	"gateway/common/rpc"
	"gateway/common/rpc_client"
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
	res := false
	switch utils.ConfigEnv {
	// 开发环境下就不调用了，直接都返回true
	// case "dev":
	// 	return true
	default:
		res = checkLoginRequest(r)
	}
	return res
}

func checkLoginRequest(r *http.Request) bool {
	// 说明见 README.md
	var whitList = utils.GetStringSliceConf(utils.ModuleApplication, "whitelist")
	match, isLogin := false, false
	realPath := r.URL.Path
	// 先判断一下请求的路径里面有没有登陆
	// 如果有则暂时标记为登陆
	if strings.Contains(realPath, "login") {
		isLogin = true
	}
	rpc_client.NewMatcherClient()
	for _, s := range whitList {
		paths := &rpc.Paths{
			Pattern:  s,
			RealPath: realPath,
		}
		match = rpc_client.Match(utils.GetMatcherClient(), paths)
		if match {
			break
		}
	}
	if match && isLogin && strings.ToLower(r.Method) == "post" {
		return true
	}
	return match
}
