package middleware

import (
	"github.com/gin-gonic/gin"
	"log"
	"runtime/debug"
)

func RecoveryMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		defer func() {
			if err := recover(); err != nil {
				// 先做一下日志记录
				log.Println(string(debug.Stack()))
			}
		}()
		c.Next()
	}
}
