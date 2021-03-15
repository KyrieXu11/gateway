package dto

import (
	"gateway/common/utils"
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
	Items       []*ServiceListItem `json:"items" form:"items" comment:"服务的信息" binding:"required"`
}

type ServiceListItem struct {
	ID          int64  `json:"id" form:"id"`
	ServiceName string `json:"service_name" form:"service_name"` // 服务名称
	ServiceDesc string `json:"service_desc" form:"service_desc"` // 服务描述
	LoadType    int    `json:"load_type" form:"load_type"`       // 类型
	ServiceAddr string `json:"service_addr" form:"service_addr"` // 服务地址
	Qps         int64  `json:"qps" form:"qps"`                   // qps
	Qpd         int64  `json:"qpd" form:"qpd"`                   // qpd
	TotalNode   int    `json:"total_node" form:"total_node"`     // 节点数
}

type ServiceSearch struct {
	ServiceType string `json:"ServiceType" form:"service_type" binding:"required"`
	ServiceId   int64  `json:"ServiceId" form:"service_id" binding:"required"`
}
