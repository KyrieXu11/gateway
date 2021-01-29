package service

import (
	"fmt"
	"gateway/dao"
	"gateway/dto"
)

type ServiceInfoService interface {
}

type ServiceInfoServiceImpl struct {
}

var serviceDao dao.ServiceInfoDao

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