package com.kyriexu.service.impl;

import com.kyriexu.dao.ServiceDao;
import com.kyriexu.dto.HttpRuleInput;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.service.HttpRuleService;
import com.kyriexu.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/3/24 20:20
 **/
@Service
public class HttpRuleServiceImpl implements HttpRuleService {

    @Autowired
    private ServiceDao serviceDao;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int add(HttpRuleInput httpRuleInput) {
        String[] ipList = httpRuleInput.getIpList().split(",");
        String[] weightList = httpRuleInput.getWeightList().split(",");
        if (ipList.length != weightList.length) {
            throw new BaseException(ResultCode.IP_WEIGHT_DIFFERENT);
        }
        String serviceName = httpRuleInput.getServiceName();
        ServiceInfo info = serviceDao.getByServiceName(serviceName);
        if (info != null) {
            throw new BaseException(ResultCode.SERVICE_NAME_ALREADY_EXIST);
        }
        int i = this.saveServiceInfo(httpRuleInput);
        return 0;
    }

    private int saveServiceInfo(HttpRuleInput httpRuleInput) {
        String serviceName = httpRuleInput.getServiceName();
        String serviceDesc = httpRuleInput.getServiceDesc();
        Date date = new Date();
        ServiceInfo info = new ServiceInfo(
                Constant.HTTPLoadType,
                serviceName, serviceDesc,
                date, date, false);
        return serviceDao.saveServiceInfo(info);
    }

    private int saveHttpRule(HttpRuleInput httpRuleInput) {
        return 0;
    }
}
