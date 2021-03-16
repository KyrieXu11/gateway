package service

import (
	"fmt"
	"gateway/common/utils"
	"gateway/dao"
	"gateway/dto"
)

type ServiceInfoService interface{}

type ServiceInfoServiceImpl struct{}

var serviceDao dao.ServiceInfoDao

var detailService ServiceDetailServiceImpl

func (p *ServiceInfoServiceImpl) GetServiceInfoList(input *dto.ServiceInput) ([]*dao.ServiceInfo, error) {
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
	list, err := p.GetServiceInfoList(input)
	var items []*dto.ServiceListItem
	for _, info := range list {
		search := &dto.ServiceSearch{
			ServiceId:   info.Id,
			ServiceType: utils.AllServiceType,
		}
		detail, err := detailService.GetServiceDetail(search)
		if err != nil {
			return nil, err
		}
		addr := getServiceAddr(detail)
		ipList := detail.LoadBalance.GetIPListByModel()
		item := &dto.ServiceListItem{
			ID:          info.Id,
			ServiceName: info.ServiceName,
			ServiceDesc: info.ServiceDesc,
			LoadType:    info.LoadType,
			ServiceAddr: addr,
			TotalNode:   len(ipList),
			Qpd:         0,
			Qps:         0,
		}
		items = append(items, item)
	}
	if err != nil {
		return nil, err
	}
	return &dto.ServiceOutput{
		Total:       p.GetTotal(input),
		CurrentPage: input.PageNo,
		Items:       items,
	}, nil
}

func getServiceAddr(detail *dao.ServiceDetail) string {
	clusterIP := utils.GetStringConf(utils.ModuleApplication, "cluster.cluster_ip")
	clusterPort := utils.GetStringConf(utils.ModuleApplication, "cluster.cluster_port")
	clusterSSLPort := utils.GetStringConf(utils.ModuleApplication, "cluster.cluster_ssl_port")
	serviceAddr := "unknown"
	if detail.Info.LoadType == utils.HTTPLoadType {
		if detail.HTTPRule.RuleType == utils.HTTPRuleTypePrefixURL {
			if detail.HTTPRule.NeedHttps == utils.NeedHttps {
				serviceAddr = fmt.Sprintf("%s:%s%s", clusterIP, clusterSSLPort, detail.HTTPRule.Rule)
			} else {
				serviceAddr = fmt.Sprintf("%s:%s%s", clusterIP, clusterPort, detail.HTTPRule.Rule)
			}
		}
		if detail.HTTPRule.RuleType == utils.HTTPRuleTypeDomain {
			serviceAddr = detail.HTTPRule.Rule
		}
	}
	if detail.Info.LoadType == utils.TCPLoadType {
		serviceAddr = fmt.Sprintf("%s:%d", clusterIP, detail.TCPRule.Port)
	}
	if detail.Info.LoadType == utils.GrpcLoadType {
		serviceAddr = fmt.Sprintf("%s:%d", clusterIP, detail.GRPCRule.Port)
	}
	return serviceAddr
}
