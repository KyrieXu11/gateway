package inter

import "github.com/gin-gonic/gin"

type DTO interface {
	ValidateAndBindParam(c *gin.Context) error
}
