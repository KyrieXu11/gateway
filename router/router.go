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
	// pingcontroller.go
	{
		router.GET("/ping", controller.Ping)
		// 设置 404
		router.NoRoute(controller.PageNotFound)
	}
	// admincontroller.go
	{
		group := router.Group("/admin")
		group.Use(middleware.GetSessionStore())
		controller.RegisterAdminController(group)
	}
	router.Static("/static", "./dist")
}
