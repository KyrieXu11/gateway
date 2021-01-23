package dto

import (
	"gateway/common/utils"
	"github.com/gin-gonic/gin"
)

type PasswordDto struct {
	Username string `json:"username" form:"username"`
	OldPass  string `json:"old_pass" form:"old_pass" binding:"required"`
	NewPass  string `json:"new_pass" form:"new_pass" binding:"required"`
}

func (p *PasswordDto) ValidateAndBindParam(c *gin.Context) error {
	return utils.ValidateAndBindParam(c, p)
}
