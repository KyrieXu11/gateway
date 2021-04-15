package controller

import (
	"gateway/dao"
	"github.com/gin-gonic/gin"
)

type DashBoardRegistrar struct{}

var (
	serviceInfoDao = dao.ServiceInfoDao{}
)

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

	// db := utils.GetDB();
	// serviceInfo := &dao.ServiceInfo{}
	// list := serviceInfoDao.GetServiceList(1, 1)
	// app := &dao.App{}
	// _, appNum, err := app.APPList(c, tx, &dto.APPListInput{PageNo: 1, PageSize: 1})
	// if err != nil {
	// 	middleware.ResponseError(c, 2002, err)
	// 	return
	// }
	// counter, err := public.FlowCounterHandler.GetCounter(public.FlowTotal)
	// if err != nil {
	// 	middleware.ResponseError(c, 2003, err)
	// 	return
	// }
	// out := &dto.PanelGroupDataOutput{
	// 	ServiceNum:      serviceNum,
	// 	AppNum:          appNum,
	// 	TodayRequestNum: counter.TotalCount,
	// 	CurrentQPS:      counter.QPS,
	// }
	// middleware.ResponseSuccess(c, out)

	return
}
