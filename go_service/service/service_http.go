package service

import (
	"gateway/dao"
	"gateway/dto"
)

type HttpRuleService struct {
}

var httpservicedao dao.HttpRuleDao

func (p *HttpRuleService) GetServiceDetailByServiceId(serviceId int64) (*dto.ServiceDetail, error) {
	// service := httpservicedao.GetServiceDetailByServiceId(serviceId)
	// if service == nil {
	// 	return nil, fmt.Errorf("没有查询到服务id为%d对应的服务", serviceId)
	// }
	// detail := &dto.ServiceDetail{
	// 	ServiceId:   serviceId,
	// 	ServiceType: "http",
	// 	ServiceItem: service,
	// }
	// return detail, nil
	return nil, nil
}
