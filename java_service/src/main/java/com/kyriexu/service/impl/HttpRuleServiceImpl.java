package com.kyriexu.service.impl;

import com.kyriexu.common.utils.Constant;
import com.kyriexu.dao.AccessControlDao;
import com.kyriexu.dao.HttpRuleDao;
import com.kyriexu.dao.LoadBalanceDao;
import com.kyriexu.dao.ServiceDao;
import com.kyriexu.dto.HttpRuleInput;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.model.AccessControl;
import com.kyriexu.model.HttpRule;
import com.kyriexu.model.LoadBalance;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.service.HttpRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final Logger logger = LoggerFactory.getLogger(HttpRuleServiceImpl.class);

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private HttpRuleDao httpRuleDao;

    @Autowired
    private AccessControlDao accessControlDao;

    @Autowired
    private LoadBalanceDao loadBalanceDao;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean add(HttpRuleInput httpRuleInput) {
        String[] ipList = httpRuleInput.getIpList().split(",");
        int roundType = httpRuleInput.getRoundType();
        if (roundType == Constant.WeightRoundType){
            String weight = httpRuleInput.getWeightList();
            String[] weightList = weight.split(",");
            if (ipList.length != weightList.length) {
                throw new BaseException(ResultCode.IP_WEIGHT_DIFFERENT);
            }
        }
        String serviceName = httpRuleInput.getServiceName();
        ServiceInfo info = serviceDao.getByServiceName(serviceName);
        if (info != null) {
            throw new BaseException(ResultCode.SERVICE_NAME_ALREADY_EXIST);
        }
        httpRuleInput.setRule(httpRuleInput.getRule().trim());
        HttpRule httpRule = httpRuleDao.getByRuleTypeAndRule(httpRuleInput.getRuleType(), httpRuleInput.getRule());
        if (httpRule != null) {
            throw new BaseException(ResultCode.SERVICE_PREFIX_DOMAIN_ALREADY_EXIST);
        }
        long serviceId = this.saveServiceInfo(httpRuleInput);
        if (serviceId < 1) {
            logger.error("[FAILED] insert http ServiceInfo");
            throw new BaseException(ResultCode.ADD_HTTP_RULE_FAIL);
        }
        logger.info("insert http service,service id : {}", serviceId);
        int res = this.saveHttpRule(httpRuleInput, serviceId);
        if (res == 0) {
            logger.error("[FAILED] insert HttpRule");
            throw new BaseException(ResultCode.ADD_HTTP_RULE_FAIL);
        }
        res = this.saveAccessControl(httpRuleInput, serviceId);
        if (res == 0) {
            logger.error("[FAILED] insert http AccessControl");
            throw new BaseException(ResultCode.ADD_HTTP_RULE_FAIL);
        }
        res = this.saveLoadBalance(httpRuleInput, serviceId);
        if (res == 0) {
            logger.error("[FAILED] insert http LoadBalance");
            throw new BaseException(ResultCode.ADD_HTTP_RULE_FAIL);
        }
        logger.info("[SUCCESS] insert HttpService Successfully");
        return true;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean update(HttpRuleInput httpRuleInput) {
        String[] ipList = httpRuleInput.getIpList().split(",");
        String[] weightList = httpRuleInput.getWeightList().split(",");
        if (ipList.length != weightList.length) {
            throw new BaseException(ResultCode.IP_WEIGHT_DIFFERENT);
        }
        if (!httpRuleInput.checkId()) {
            throw new BaseException(ResultCode.SERVICE_ID_ILLEGAL);
        }
        boolean b = this.updateServiceInfo(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update http ServiceInfo");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        b = this.updateHttpRule(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update HttpRule");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        b = this.updateAccessControl(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update http AccessControl");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        b = this.updateLoadBalance(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update http LoadBalance");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        logger.info("[SUCCESS] update HttpService Successfully");
        return true;
    }

    private long saveServiceInfo(HttpRuleInput httpRuleInput) {
        String serviceName = httpRuleInput.getServiceName();
        String serviceDesc = httpRuleInput.getServiceDesc();
        Date date = new Date();
        ServiceInfo info = new ServiceInfo(
                Constant.HTTPLoadType,
                serviceName, serviceDesc,
                date, date, false);
        int i = serviceDao.saveServiceInfo(info);
        if (i == 0) {
            info.setId(0L);
        }
        return info.getId();
    }

    private int saveHttpRule(HttpRuleInput httpRuleInput, long serviceId) {
        HttpRule rule = new HttpRule(
                serviceId,
                httpRuleInput.getRuleType(),
                httpRuleInput.getRule(),
                httpRuleInput.getNeedHttps(),
                httpRuleInput.getNeedStripUri(),
                httpRuleInput.getNeedWebSocket(),
                httpRuleInput.getUrlRewrite(),
                httpRuleInput.getHeaderTransfor()
        );
        return httpRuleDao.add(rule);
    }

    private int saveAccessControl(HttpRuleInput httpRuleInput, Long serviceId) {
        AccessControl accessControl = new AccessControl(
                serviceId,
                httpRuleInput.getOpenAuth(),
                httpRuleInput.getBlackList(),
                httpRuleInput.getWhiteList(),
                httpRuleInput.getClientipFlowLimit(),
                httpRuleInput.getServiceFlowLimit()
        );
        return accessControlDao.save(accessControl);
    }

    private int saveLoadBalance(HttpRuleInput httpRuleInput, Long serviceId) {
        LoadBalance loadBalance = new LoadBalance(
                serviceId,
                httpRuleInput.getRoundType(),
                httpRuleInput.getIpList(),
                httpRuleInput.getWeightList(),
                httpRuleInput.getUpstreamConnectTimeout(),
                httpRuleInput.getUpstreamHeaderTimeout(),
                httpRuleInput.getUpstreamIdleTimeout(),
                httpRuleInput.getUpstreamMaxIdle()
        );
        return loadBalanceDao.save(loadBalance);
    }

    private boolean updateServiceInfo(HttpRuleInput httpRuleInput) {
        ServiceInfo info = new ServiceInfo(
                httpRuleInput.getId(),
                Constant.HTTPLoadType,
                httpRuleInput.getServiceName(),
                httpRuleInput.getServiceDesc(),
                null,
                new Date(),
                false
        );
        int i = serviceDao.updateServiceInfo(info);
        return i > 0;
    }

    private boolean updateHttpRule(HttpRuleInput httpRuleInput) {
        HttpRule rule = new HttpRule(
                httpRuleInput.getId(),
                httpRuleInput.getRuleType(),
                httpRuleInput.getRule(),
                httpRuleInput.getNeedHttps(),
                httpRuleInput.getNeedStripUri(),
                httpRuleInput.getNeedWebSocket(),
                httpRuleInput.getUrlRewrite(),
                httpRuleInput.getHeaderTransfor());
        int i = httpRuleDao.updateHttpRule(rule);
        return i > 0;
    }

    private boolean updateAccessControl(HttpRuleInput httpRuleInput) {
        AccessControl accessControl = new AccessControl(
                httpRuleInput.getId(),
                httpRuleInput.getOpenAuth(),
                httpRuleInput.getBlackList(),
                httpRuleInput.getWhiteList(),
                httpRuleInput.getClientipFlowLimit(),
                httpRuleInput.getServiceFlowLimit()
        );
        int i = accessControlDao.update(accessControl);
        return i > 0;
    }

    private boolean updateLoadBalance(HttpRuleInput httpRuleInput) {
        LoadBalance loadBalance = new LoadBalance(
                httpRuleInput.getId(),
                httpRuleInput.getRoundType(),
                httpRuleInput.getIpList(),
                httpRuleInput.getWeightList(),
                httpRuleInput.getUpstreamConnectTimeout(),
                httpRuleInput.getUpstreamHeaderTimeout(),
                httpRuleInput.getUpstreamIdleTimeout(),
                httpRuleInput.getUpstreamMaxIdle()
        );
        int i = loadBalanceDao.update(loadBalance);
        return i > 0;
    }
}
