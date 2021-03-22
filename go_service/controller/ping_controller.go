package controller

import (
	"gateway/common/utils"
	"github.com/gin-gonic/gin"
)

func Ping(c *gin.Context) {
	utils.ResponseSuccessObj(c, "pong", nil)
}

func PageNotFound(c *gin.Context) {
	path := c.Request.URL.Path
	utils.ResponseErrorCM(c, utils.NotFoundError, path+" 页面未找到")
}
