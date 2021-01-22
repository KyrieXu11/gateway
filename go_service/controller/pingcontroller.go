package controller

import (
	"gateway/common/utils"
	"github.com/gin-gonic/gin"
)

func Ping(c *gin.Context) {
	utils.ResponseSuccessObj(c, "pong", nil)
}

func PageNotFound(c *gin.Context) {
	utils.ResponseErrorCM(c, utils.NOT_FOUND_ERROR, "页面未找到")
}
