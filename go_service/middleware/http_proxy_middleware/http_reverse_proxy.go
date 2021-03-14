package http_proxy_middleware

import (
	"gateway/common/utils"
	"gateway/dao"
	"gateway/middleware/reverse_proxy"
	"gateway/service"
	"github.com/gin-gonic/gin"
)

func HttpReverseProxy() gin.HandlerFunc {
	return func(c *gin.Context) {
		s, ok := c.Get("service")
		if !ok {
			utils.ResponseErrorCM(c, 2001, "the service does not exist")
			c.Abort()
			return
		}
		// TODO: complete httpreverseproxy middleware
		serviceDetail := s.(*dao.ServiceDetail)
		lb, err := service.LoadBalanceHandler.GetLoadBalancer(serviceDetail)
		if err != nil {
			utils.ResponseErrorCM(c, 2002, err.Error())
			c.Abort()
			return
		}
		proxy := reverse_proxy.NewLoadBalanceReverseProxy(c, lb)
		proxy.ServeHTTP(c.Writer,c.Request)
		c.Next()
	}
}
