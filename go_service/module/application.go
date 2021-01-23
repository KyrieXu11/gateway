package module

import (
	"gateway/common/config"
	"gateway/common/log"
	"gateway/common/utils"
	"github.com/gin-gonic/gin"
)

var application = "application"

func InitApplication() error {
	app := &config.Application{}
	utils.Unmarshal(application, app)

	// 设置日志输出的级别
	if app.Log.Level != "" {
		log.SetLoggerLevel(app.Log.Level)
	}

	// 设置gin的模式
	gin.SetMode(app.Gin.Mode)
	return nil
}
