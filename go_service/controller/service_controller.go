package controller

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dto"
	"gateway/service"
	"github.com/gin-gonic/gin"
	"strconv"
)

type ServiceRegistrar struct{}

var (
	serviceInfoService   service.ServiceInfoServiceImpl
	serviceDetailService service.ServiceDetailServiceImpl
)

func RegisterServiceController(g *gin.RouterGroup) {
	c := &ServiceRegistrar{}
	g.GET("/list", c.GetServiceList)
	g.GET("/detail", c.GetServiceDetail)
	g.POST("/add_http_service", c.AddHttpService)
}

// 获取所有的服务列表，通过分页的方式
func (p *ServiceRegistrar) GetServiceList(c *gin.Context) {
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

// 获取服务详情
func (p *ServiceRegistrar) GetServiceDetail(c *gin.Context) {
	serviceType := c.Query("service_type")
	serviceId, err := strconv.ParseInt(c.Query("service_id"), utils.NumBase10, utils.IntegerBitSize64)
	if err != nil {
		utils.ResponseErrorM(c, "检查服务ID是否正确")
		return
	}
	search := &dto.ServiceSearch{
		ServiceType: serviceType,
		ServiceId:   serviceId,
	}
	detail, err := serviceDetailService.GetServiceDetail(search)
	if err != nil {
		utils.ResponseErrorM(c, err.Error())
		return
	}
	utils.ResponseSuccessObj(c, "成功", detail)
}

// 添加http服务
func (p *ServiceRegistrar) AddHttpService(c *gin.Context) {
	s := &dto.ServiceAddHTTPInput{}
	if err := s.ValidateAndBindParam(c); err != nil {
		log.Error(err.Error())
		utils.ResponseErrorM(c, err.Error())
		return
	}

}
