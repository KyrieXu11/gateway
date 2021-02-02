package dto

import (
	"gateway/common/utils"
	"gateway/dao"
	"github.com/gin-gonic/gin"
)

type ServiceInput struct {
	Info     string `json:"info" form:"info" comment:"关键词" binding:""`
	PageNo   int    `json:"page_no" form:"page_no" comment:"页数"`
	PageSize int    `json:"page_size" form:"page_size" comment:"每一页的条数"`
}

func (p *ServiceInput) ValidateAndBindParam(c *gin.Context) error {
	return utils.ValidateAndBindParam(c, p)
}

// 返回给前端的对象
type ServiceOutput struct {
	Total       int64              `json:"total" form:"total" comment:"服务总数" binding:"required"`
	CurrentPage int                `json:"current_page" form:"current_page" comment:"当前在第几页" binding:"required"`
	Items       []*dao.ServiceInfo `json:"items" form:"items" comment:"服务的信息" binding:"required"`
}

type ServiceDetail struct {
	ServiceType string      `json:"service_type" form:"service_type" binding:"required"`
	ServiceId   int64       `json:"service_id" form:"service_id" binding:"required"`
	ServiceItem interface{} `json:"service_item"`
}

