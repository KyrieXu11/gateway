package controller

import (
	"github.com/gin-gonic/gin"
)

type DashBoardRegistrar struct{}

func RegisterDashBoardController(g *gin.RouterGroup) {

}

func (p *DashBoardRegistrar) PanelGroupData(c *gin.Context) {
	// input := &dto.ServiceInput{
	// 	PageSize: 1,
	// 	PageNo:   1,
	// }
	// list, err := serviceInfoService.GetServiceInfoList(input)
	// if err != nil {
	// 	utils.ResponseErrorM(c, err.Error())
	// 	return
	// }
	// serviceNum := len(list)

	return
}
