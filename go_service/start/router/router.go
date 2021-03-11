package router

import (
	"gateway/controller"
	"gateway/middleware"
	"github.com/gin-gonic/gin"
)

// 初始化路由
func InitRouter(middleware ...gin.HandlerFunc) *gin.Engine {
	router := gin.Default()
	router.Use(middleware...)
	// 注册控制器
	registerRouter(router)
	return router
}

// 注册控制器
func registerRouter(router *gin.Engine) {
	router.Use(middleware.Cors(),
		middleware.GetCookieSessionFunc(),
		middleware.CheckLogin())
	// ping_controller.go
	{
		router.GET("/ping", controller.Ping)
		// 设置 404
		router.NoRoute(controller.PageNotFound)
	}

	// admin_controller.go
	{
		group := router.Group("/admin")
		controller.RegisterAdminController(group)
	}

	// service_controller.go
	{
		group := router.Group("/service")
		controller.RegisterServiceController(group)
	}
	router.Static("/static", "./dist")
}
