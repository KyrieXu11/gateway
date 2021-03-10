package module

import (
	"gateway/common/config"
	"gateway/common/log"
	"gateway/common/rpc_client"
	"gateway/common/utils"
	"gateway/middleware"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
)

func InitApplication() error {
	app := &config.Application{}
	utils.Unmarshal(utils.ModuleApplication, app)

	// 设置日志输出的级别
	if app.Log.Level != "" {
		log.SetLoggerLevel(app.Log.Level)
	}

	// 设置gin的模式
	gin.SetMode(app.Gin.Mode)

	filter := utils.GetBoolConf(utils.ModuleApplication, "admin.filter")
	if filter {
		// 在这里执行的好处是提前把连接建立好了
		// 不用等到请求的时候把连接建立
		// 坏处就是...不能感知服务器
		rpc_client.NewMatcherClient()
	}

	maxAge := utils.GetIntConf(utils.ModuleApplication, "session.max_age")
	options := &sessions.Options{
		Path:   "/",
		MaxAge: maxAge * utils.Minute,
	}
	middleware.InitCookieSessionConf("session", options, []byte("secret"))
	log.Info("application initialized")
	return nil
}
