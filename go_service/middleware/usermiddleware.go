package middleware

import (
	"gateway/common/log"
	"gateway/common/rpc"
	"gateway/common/rpc_client"
	"gateway/common/utils"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"net/http"
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
	filter := utils.GetBoolConf(utils.ModuleApplication, "admin.filter")
	switch filter {
	// 开发环境下就不调用了，直接都返回true
	case false:
		return true
	default:
		res = checkLoginRequest(r)
	}
	return res
}

func checkLoginRequest(r *http.Request) bool {
	req := buildRpcRequest(r)
	rpc_client.NewMatcherClient()
	client := utils.GetMatcherClient()
	return rpc_client.Match(client, req)
}

func buildRpcRequest(r *http.Request) *rpc.GoRequest {
	m := make(map[string]*rpc.Header)
	for k, v := range r.Header {
		m[k] = new(rpc.Header)
		m[k].HeaderValue = v
	}
	req := &rpc.GoRequest{
		RealPath:   r.RequestURI,
		Method:     r.Method,
		Proto:      r.Proto,
		Header:     m,
		RemoteAddr: r.RemoteAddr,
	}
	return req
}
