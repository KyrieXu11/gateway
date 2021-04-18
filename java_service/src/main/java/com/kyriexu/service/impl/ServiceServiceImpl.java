package com.kyriexu.service.impl;

import com.kyriexu.common.utils.ComponentUtils;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.HttpUtils;
import com.kyriexu.common.utils.Utils;
import com.kyriexu.dao.AccessControlDao;
import com.kyriexu.dao.GrpcRuleDao;
import com.kyriexu.dao.HttpRuleDao;
import com.kyriexu.dao.LoadBalanceDao;
import com.kyriexu.dao.ServiceDao;
import com.kyriexu.dao.TcpRuleDao;
import com.kyriexu.dto.DashServiceStatOutput;
import com.kyriexu.dto.ListDto;
import com.kyriexu.dto.SearchInput;
import com.kyriexu.dto.ServiceListItem;
import com.kyriexu.dto.ServiceSearch;
import com.kyriexu.dto.ServiceStatItemOutput;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.model.AccessControl;
import com.kyriexu.model.GrpcRule;
import com.kyriexu.model.HttpRule;
import com.kyriexu.model.LoadBalance;
import com.kyriexu.model.PageBean;
import com.kyriexu.model.ServiceDetail;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.model.TcpRule;
import com.kyriexu.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:15
 **/
@Service
public class ServiceServiceImpl implements ServiceService {

    public static final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

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

    @Autowired
    private HttpServletRequest request;

    @Override
    public PageBean<ServiceListItem> getPageBean(SearchInput input) {
        List<ServiceInfo> infoList = this.getServiceInfoList(input);
        List<ServiceDetail> detailList = new ArrayList<>();
        infoList.forEach(info -> {
            ServiceDetail detail = getServiceDetail(info);
            detailList.add(detail);
        });
        String url = String.format("http://%s%s", componentUtils.getGoServiceAddr(), request.getRequestURI());
        logger.info("[INTERNAL CALL] url : {}", url);
        ListDto<ServiceDetail> dto = new ListDto<>(detailList);
        try {
            List<ServiceListItem> items = HttpUtils.post(url, dto,List.class);
            return new PageBean<>(this.getTotalPage(input), input.getPage(), items);
        } catch (IOException e) {
            logger.error("[FAIL] get service list item cause: ", e);
            throw new BaseException(ResultCode.INTERNAL_EXCEPTION);
        }
    }

    @Override
    public ServiceDetail getServiceDetail(ServiceSearch search) {
        ServiceDetail detail = new ServiceDetail();
        // String serviceType = search.getServiceType();
        // Long serviceId = search.getServiceId();
        // if (!Utils.contains(Constant.SERVICE_TYPE, serviceType)) {
        //     throw new BaseException(ResultCode.NO_SUCH_SERVICE_TYPE);
        // }
        // ServiceInfo info = serviceDao.get(serviceId);
        // detail.setInfo(info);
        // setRule(search, detail);
        // LoadBalance loadBalance = loadBalanceDao.get(serviceId);
        // AccessControl accessControl = accessControlDao.get(serviceId);
        // detail.setLoadBalance(loadBalance);
        // detail.setAccessControl(accessControl);
        return detail;
    }

    @Override
    public ServiceDetail getServiceDetail(ServiceInfo info) {
        ServiceDetail detail = new ServiceDetail();
        Long serviceId = info.getId();
        if (StringUtils.isEmpty(info.getServiceName())) {
            info = serviceDao.get(serviceId);
        }
        detail.setInfo(info);
        setRule(info, detail);
        LoadBalance loadBalance = loadBalanceDao.get(serviceId);
        AccessControl accessControl = accessControlDao.get(serviceId);
        detail.setLoadBalance(loadBalance);
        detail.setAccessControl(accessControl);
        return detail;
    }

    @Override
    public int getTotalPage(SearchInput input) {
        int size = input.getSize();
        int res = serviceDao.getTotal(input.getInfo());
        if (res % size != 0) {
            return res / size + 1;
        }
        return res / size;
    }

    @Override
    public int saveServiceInfo(ServiceInfo serviceInfo) {
        return serviceDao.saveServiceInfo(serviceInfo);
    }

    @Override
    public DashServiceStatOutput countByLoadType() {
        List<ServiceStatItemOutput> list = serviceDao.countByLoadType();
        List<String> legend = new ArrayList<>();
        DashServiceStatOutput out = new DashServiceStatOutput();
        list.forEach(item -> {
            String name = Constant.SERVICE_TYPE[item.getLoadType()];
            item.setName(name);
            legend.add(name);
        });
        out.setData(list);
        out.setLegend(legend);
        return out;
    }

    @Override
    public List<ServiceDetail> getServiceDetails(SearchInput input) {
        List<ServiceInfo> list = this.getServiceInfoList(input);
        List<ServiceDetail> details = new ArrayList<>();
        list.forEach(info -> {
            ServiceDetail detail = getServiceDetail(info);
            details.add(detail);
        });
        return details;
    }

    @Override
    public boolean del(Long serviceId) {
        ServiceInfo info = serviceDao.get(serviceId);
        if (info != null && info.isDeleted()) {
            throw new BaseException(ResultCode.SERVICE_ALREADY_DELETED);
        } else if (info == null) {
            throw new BaseException(ResultCode.SERVICE_NO_EXIST);
        }
        info = new ServiceInfo();
        info.setId(serviceId);
        info.setUpdateAt(new Date());
        info.setDeleted(true);
        int i = serviceDao.updateServiceInfo(info);
        return i > 0;
    }

    @Override
    public List<ServiceInfo> getServiceInfoList(SearchInput searchInput) {
        int size = searchInput.getSize();
        int page = Utils.getPage(searchInput.getPage(), size);
        return serviceDao.getServiceInfoList(page, size, searchInput.getInfo());
    }

    // private void setRule(ServiceSearch search, ServiceDetail detail) {
    //     String serviceType = search.getServiceType();
    //     Long serviceId = search.getServiceId();
    //     switch (serviceType) {
    //         case Constant.SERVICE_HTTP:
    //             HttpRule httpRule = httpRuleDao.get(serviceId);
    //             detail.setHttpRule(httpRule);
    //             break;
    //         case Constant.SERVICE_TCP:
    //             TcpRule tcpRule = tcpRuleDao.get(serviceId);
    //             detail.setTcpRule(tcpRule);
    //             break;
    //         case Constant.SERVICE_GRPC:
    //             GrpcRule grpcRule = grpcRuleDao.get(serviceId);
    //             detail.setGrpcRule(grpcRule);
    //             break;
    //         case Constant.SERVICE_ALL:
    //             search.setServiceType(Constant.SERVICE_HTTP);
    //             setRule(search, detail);
    //             search.setServiceType(Constant.SERVICE_TCP);
    //             setRule(search, detail);
    //             search.setServiceType(Constant.SERVICE_GRPC);
    //             setRule(search, detail);
    //             search.setServiceType(Constant.SERVICE_ALL);
    //             break;
    //     }
    // }

    private void setRule(ServiceInfo info, ServiceDetail detail) {
        Long serviceId = info.getId();
        switch (info.getLoadType()) {
            case Constant.HTTPLoadType:
                HttpRule httpRule = httpRuleDao.get(serviceId);
                detail.setHttpRule(httpRule);
                break;
            case Constant.TCPLoadType:
                TcpRule tcpRule = tcpRuleDao.get(serviceId);
                detail.setTcpRule(tcpRule);
                break;
            case Constant.GrpcLoadType:
                GrpcRule grpcRule = grpcRuleDao.get(serviceId);
                detail.setGrpcRule(grpcRule);
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
