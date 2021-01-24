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
