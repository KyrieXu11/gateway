package dto

import (
	"github.com/gin-gonic/gin"
)

type AdminDto struct {
	Username string `json:"username" form:"username" comment:"用户名" binding:"required" example:"admin"`
	Password string `json:"password" form:"password" comment:"密码" binding:"required" example:"123456"`
}

func (p *AdminDto) ValidateAndBindParam(c *gin.Context) error {
	if err := c.ShouldBindJSON(p); err != nil {
		return err
	}
	return nil
}
