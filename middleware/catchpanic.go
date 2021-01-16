package middleware

import (
	"gateway/common/log"
	"github.com/gin-gonic/gin"
	"runtime/debug"
)

func RecoveryMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		defer func() {
			if err := recover(); err != nil {
				// 做一下日志记录
				log.Info(string(debug.Stack()))
			}
		}()
		c.Next()
	}
}
