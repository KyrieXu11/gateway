package service

import (
	"fmt"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/dto"
	"strings"
)

type ServiceInfoService interface{}

type ServiceInfoServiceImpl struct{}

var serviceDao dao.ServiceInfoDao

var httpDao dao.HttpRuleDao

var tcpDao dao.TcpRuleDao

var grpcDao dao.GrpcRuleDao

func (p *ServiceInfoServiceImpl) GetServiceList(input *dto.ServiceInput) ([]*dao.ServiceInfo, error) {
	page := input.PageNo
	size := input.PageSize
	page, err := utils.GetPage(page, size)
	if err != nil {
		return nil, err
	}
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
		httpRule, err := httpDao.GetHttpDetail(detail.ServiceId)
		if err != nil {
			log.Error(err.Error())
			return err
		}
		detail.ServiceItem = httpRule
		break
	case "tcp":
		tcpRule, err := tcpDao.GetTcpDetail(detail.ServiceId)
		if err != nil {
			log.Error(err.Error())
			return err
		}
		detail.ServiceItem = tcpRule
		break
	case "grpc":
		grpcRule, err := grpcDao.GetgRpcDetail(detail.ServiceId)
		if err != nil {
			log.Error(err.Error())
			return err
		}
		detail.ServiceItem = grpcRule
	}
	return nil
}
