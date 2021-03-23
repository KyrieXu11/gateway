package middleware

import (
	"gateway/common/utils"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
)

func ValidatorMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		val := validator.New()
		c.Set(utils.ValidatorKey, val)
		c.Next()
	}
}
