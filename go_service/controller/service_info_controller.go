package controller

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dto"
	"gateway/service"
	"github.com/gin-gonic/gin"
	"strconv"
)

type ServiceListRegistrator struct {
}

var serviceInfoService service.ServiceInfoServiceImpl

func RegisterServiceListController(g *gin.RouterGroup) {
	c := &ServiceListRegistrator{}
	g.GET("/list", c.GetServiceList)
}

// 获取所有的服务列表，通过分页的方式
func (p *ServiceListRegistrator) GetServiceList(c *gin.Context) {
	page, err := strconv.Atoi(c.DefaultQuery("page", "0"))
	if ok := utils.CheckErrorAndResponse(c, err); ok {
		log.Error(err.Error())
		return
	}
	size, err := strconv.Atoi(c.DefaultQuery("size", "10"))
	if ok := utils.CheckErrorAndResponse(c, err); ok {
		log.Error(err.Error())
		return
	}
	serviceInput := &dto.ServiceInput{
		PageNo:   page,
		PageSize: size,
	}

	out, err := serviceInfoService.GetPageBean(serviceInput)
	if err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, err.Error())
		return
	}
	utils.ResponseSuccessObj(c, "查询成功", out)
}
