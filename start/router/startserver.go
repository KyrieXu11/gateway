package router

import (
	"gateway/common/log"
	"gateway/common/utils"
	"github.com/gin-gonic/gin"
)

// 启动服务器
func ListenAndServe(r *gin.Engine) {
	port := utils.GetStringConf(utils.ModuleApplication, "server.port")
	go func() {
		log.Info("Server start on port:" + port)
		if err := r.Run(":" + port); err != nil {
			log.Error(err.Error())
			return
		}
	}()
}
