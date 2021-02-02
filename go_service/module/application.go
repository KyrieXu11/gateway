package module

import (
	"gateway/common/config"
	"gateway/common/log"
	"gateway/common/rpc_client"
	"gateway/common/utils"
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

	log.Info("application initialized")
	return nil
}
