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
	g.GET("/detail", c.GetServiceDetail)
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

func (p *ServiceListRegistrator) GetServiceDetail(c *gin.Context) {
	serviceType := c.Query("service_type")
	serviceId, err := strconv.ParseInt(c.Query("service_id"), utils.NUMBASE10, utils.INTEGER_BIT_SIZE64)
	if err != nil {
		utils.ResponseErrorM(c, "检查服务ID是否正确")
		return
	}
	detail := &dto.ServiceDetail{
		ServiceType: serviceType,
		ServiceId:   serviceId,
	}
	if err := serviceInfoService.GetServiceDetail(detail); err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, err.Error())
		return
	}
	utils.ResponseSuccessObj(c, "成功", detail)
}
