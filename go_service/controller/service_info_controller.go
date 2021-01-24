package controller

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dto"
	"gateway/service"
	"github.com/gin-gonic/gin"
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
	serviceInput := &dto.ServiceInput{}
	if err := serviceInput.ValidateAndBindParam(c); err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, "参数传递的有问题，检查一下参数吧")
		return
	}
	list, err := serviceInfoService.GetServiceList(serviceInput)
	if err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c,err.Error())
		return
	}
	res := &dto.ServiceOutput{
		Items: list,
	}
	utils.ResponseSuccessObj(c, "查询成功", res)
}
