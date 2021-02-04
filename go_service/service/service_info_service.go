package service

import (
	"fmt"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/dto"
	"strings"
)

type ServiceInfoService interface {
}

type ServiceInfoServiceImpl struct {
}

var serviceDao dao.ServiceInfoDao

var httpDao dao.ServiceHttpDao

var serviceDetailDao dao.ServiceDetailDao

func (p *ServiceInfoServiceImpl) GetServiceList(input *dto.ServiceInput) ([]*dao.ServiceInfo, error) {
	page := input.PageNo
	size := input.PageSize
	if size > 50 {
		return nil, fmt.Errorf("一页最多只能查询50条记录哦")
	}
	page = (page - 1) * size
	list := serviceDao.GetServiceList(page, size)
	return list, nil
}

func (p *ServiceInfoServiceImpl) GetTotal(input *dto.ServiceInput) int64 {
	return serviceDao.GetTotalPages(input.PageSize)
}

func (p *ServiceInfoServiceImpl) GetPageBean(input *dto.ServiceInput) (*dto.ServiceOutput, error) {
	list, err := p.GetServiceList(input)
	if err != nil {
		return nil, err
	}
	return &dto.ServiceOutput{
		Total:       p.GetTotal(input),
		Items:       list,
		CurrentPage: input.PageNo,
	}, nil
}

func (p *ServiceInfoServiceImpl) GetServiceDetail(detail *dto.ServiceDetail) error {
	detail.ServiceType = strings.ToLower(detail.ServiceType)
	typeList := []string{"http", "tcp", "grpc"}
	if contains := utils.SliceContains(typeList, detail.ServiceType); !contains {
		return fmt.Errorf("检查服务类型是否正确")
	}
	switch detail.ServiceType {
	case "http":
		var httpService = dao.ServiceHttp{}
		tmp := serviceDetailDao.GetServiceDetailByServiceId(detail.ServiceId, httpService).(dao.ServiceHttp)
		httpService = tmp
		detail.ServiceItem = httpService
		break
	case "tcp":
		var tcpService dao.ServiceTcp
		tmp := serviceDetailDao.GetServiceDetailByServiceId(detail.ServiceId, tcpService).(dao.ServiceTcp)
		tcpService = tmp
		detail.ServiceItem = tcpService
		break
	}
	return nil
}
