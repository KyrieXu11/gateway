package com.kyriexu.service.impl;

import com.kyriexu.dao.AccessControlDao;
import com.kyriexu.dao.GrpcRuleDao;
import com.kyriexu.dao.HttpRuleDao;
import com.kyriexu.dao.LoadBalanceDao;
import com.kyriexu.dao.ServiceDao;
import com.kyriexu.dao.TcpRuleDao;
import com.kyriexu.dto.ServiceInput;
import com.kyriexu.dto.ServiceSearch;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.model.AccessControl;
import com.kyriexu.model.GrpcRule;
import com.kyriexu.model.HttpRule;
import com.kyriexu.model.LoadBalance;
import com.kyriexu.model.ServiceDetail;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.model.ServiceListItem;
import com.kyriexu.model.ServicePageBean;
import com.kyriexu.model.TcpRule;
import com.kyriexu.service.ServiceService;
import com.kyriexu.utils.ComponentUtils;
import com.kyriexu.utils.Constant;
import com.kyriexu.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:15
 **/
@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private HttpRuleDao httpRuleDao;

    @Autowired
    private TcpRuleDao tcpRuleDao;

    @Autowired
    private GrpcRuleDao grpcRuleDao;

    @Autowired
    private LoadBalanceDao loadBalanceDao;

    @Autowired
    private AccessControlDao accessControlDao;

    @Autowired
    private ComponentUtils componentUtils;

    @Override
    public ServicePageBean getPageBean(ServiceInput serviceInput) {
        int page = Utils.getPage(serviceInput.getPage(), serviceInput.getSize());
        List<ServiceInfo> infoList = serviceDao.getServiceInfoList(page, serviceInput.getSize(), serviceInput.getInfo());
        // TODO: 优化查询，改成批量查询在内存当中创建 ServiceDetail 对象
        List<ServiceListItem> items = new ArrayList<>(infoList.size());
        infoList.forEach(info -> {
            ServiceSearch search = new ServiceSearch(info.getId(), Constant.SERVICE_ALL);
            ServiceDetail detail = getServiceDetail(search);
            String addr = getAddr(detail);
            List<String> ipList = detail.getLoadBalance().getIPListByModel();
            ServiceListItem item = new ServiceListItem(
                    info.getId(),
                    info.getServiceName(),
                    info.getServiceDesc(),
                    info.getLoadType(),
                    addr,
                    0L,
                    0L,
                    ipList.size()
            );
            items.add(item);
        });
        return new ServicePageBean(this.getTotalPage(serviceInput), serviceInput.getPage(), items);
    }

    @Override
    public ServiceDetail getServiceDetail(ServiceSearch search) {
        ServiceDetail detail = new ServiceDetail();
        String serviceType = search.getServiceType();
        Long serviceId = search.getServiceId();
        if (!Utils.contains(Constant.SERVICE_TYPE, serviceType)) {
            throw new BaseException(ResultCode.NO_SUCH_SERVICE_TYPE);
        }
        setRule(search, detail);
        LoadBalance loadBalance = loadBalanceDao.get(serviceId);
        AccessControl accessControl = accessControlDao.get(serviceId);
        detail.setLoadBalance(loadBalance);
        detail.setAccessControl(accessControl);
        return detail;
    }

    @Override
    public int getTotalPage(ServiceInput input) {
        int size = input.getSize();
        int res = serviceDao.getTotal(size);
        if (res % size != 0) {
            return res / size + 1;
        }
        return res / size;
    }

    @Override
    public long saveServiceInfo(ServiceInfo serviceInfo) {
        return serviceDao.saveServiceInfo(serviceInfo);
    }

    private void setRule(ServiceSearch search, ServiceDetail detail) {
        String serviceType = search.getServiceType();
        Long serviceId = search.getServiceId();
        ServiceInfo info = serviceDao.get(serviceId);
        detail.setInfo(info);
        switch (serviceType) {
            case Constant.SERVICE_HTTP:
                HttpRule httpRule = httpRuleDao.get(serviceId);
                detail.setHttpRule(httpRule);
                break;
            case Constant.SERVICE_TCP:
                TcpRule tcpRule = tcpRuleDao.get(serviceId);
                detail.setTcpRule(tcpRule);
                break;
            case Constant.SERVICE_GRPC:
                GrpcRule grpcRule = grpcRuleDao.get(serviceId);
                detail.setGrpcRule(grpcRule);
                break;
            case Constant.SERVICE_ALL:
                ServiceSearch s = new ServiceSearch(serviceId, Constant.SERVICE_HTTP);
                setRule(s, detail);
                s.setServiceType(Constant.SERVICE_TCP);
                setRule(s, detail);
                s.setServiceType(Constant.SERVICE_GRPC);
                setRule(s, detail);
                break;
        }
    }

    private String getAddr(ServiceDetail detail) {
        String addr = "unknown";
        if (Constant.HTTPLoadType == detail.getInfo().getLoadType()) {
            if (detail.getHttpRule().getRuleType() == Constant.HTTPRuleTypePrefixURL) {
                if (detail.getHttpRule().getNeedHttps() == Constant.NeedHttps) {
                    addr = String.format("%s:%s%s", componentUtils.getClusterIp(),
                            componentUtils.getClusterSSLPort(),
                            detail.getHttpRule().getRule());
                } else {
                    addr = String.format("%s:%s%s", componentUtils.getClusterIp(),
                            componentUtils.getClusterPort(),
                            detail.getHttpRule().getRule());
                }
            }
            if (detail.getHttpRule().getRuleType() == Constant.HTTPRuleTypeDomain) {
                addr = detail.getHttpRule().getRule();
            }
        }
        if (detail.getInfo().getLoadType() == Constant.TCPLoadType) {
            addr = String.format("%s:%d", componentUtils.getClusterIp(), detail.getTcpRule().getPort());
        }
        if (detail.getInfo().getLoadType() == Constant.GrpcLoadType) {
            addr = String.format("%s:%d", componentUtils.getClusterIp(), detail.getGrpcRule().getPort());
        }
        return addr;
    }
}
