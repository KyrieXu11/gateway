package http_proxy

import (
	"gateway/middleware/http_proxy_middleware"
	"github.com/gin-gonic/gin"
)

// 初始化http_proxy的router
func InitRouter(middleware ...gin.HandlerFunc) *gin.Engine {
	// TODO: change type of creating a new gin engine
	router := gin.New()
	router.Use(middleware...)
	UseMiddleWare(router)
	// 注册控制器
	return router
}

func UseMiddleWare(r *gin.Engine) {
	r.Use(
		http_proxy_middleware.HTTPAccessModeMiddleware(),
	)
}
