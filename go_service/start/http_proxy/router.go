package http_proxy

import (
	"github.com/gin-gonic/gin"
)

// 初始化http_proxy的router
func InitRouter(middleware ...gin.HandlerFunc) *gin.Engine {
	// TODO: change type of creating a new gin engine
	router := gin.New()
	router.Use(middleware...)
	// 注册控制器
	return router
}
