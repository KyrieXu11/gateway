package http_proxy_middleware

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/service"
	"github.com/gin-gonic/gin"
	"go.uber.org/zap"
)

func HTTPAccessModeMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		s, err := service.ServiceManagerHandler.HTTPAccessMode(c)
		if err != nil {
			utils.ResponseErrorM(c, err.Error())
			c.Abort()
			return
		}
		log.Info("", zap.Any("service", s))
		c.Set("service", s)
		c.Next()
	}
}
