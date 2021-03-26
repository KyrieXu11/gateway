package com.kyriexu.service.impl;

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
import com.kyriexu.utils.Constant;
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
        String[] weightList = httpRuleInput.getWeightList().split(",");
        if (ipList.length != weightList.length) {
            throw new BaseException(ResultCode.IP_WEIGHT_DIFFERENT);
        }
        String serviceName = httpRuleInput.getServiceName();
        ServiceInfo info = serviceDao.getByServiceName(serviceName);
        if (info != null) {
            throw new BaseException(ResultCode.SERVICE_NAME_ALREADY_EXIST);
        }
        HttpRule httpRule = httpRuleDao.getByRuleTypeAndRule(httpRuleInput.getRuleType(), httpRuleInput.getRule());
        if (httpRule != null) {
            throw new BaseException(ResultCode.SERVICE_PREFIX_DOMAIN_ALEADY_EXIST);
        }
        long serviceId = this.saveServiceInfo(httpRuleInput);
        if (serviceId < 1) {
            logger.error("[FAILED] insert ServiceInfo");
            throw new RuntimeException("[FAILED] insert ServiceInfo, rollback");
        }
        logger.info("service id : {}", serviceId);
        int res = this.saveHttpRule(httpRuleInput, serviceId);
        if (res == 0) {
            logger.error("[FAILED] insert HttpRule");
            throw new RuntimeException("[FAILED] insert HttpRule, rollback");
        }
        res = this.saveAccessControl(httpRuleInput, serviceId);
        if (res == 0) {
            logger.error("[FAILED] insert AccessControl");
            throw new RuntimeException("[FAILED] insert AccessControl, rollback");
        }
        res = this.saveLoadBalance(httpRuleInput, serviceId);
        if (res == 0) {
            logger.error("[FAILED] insert LoadBalance");
            throw new RuntimeException("[FAILED] insert LoadBalance, rollback");
        }
        logger.info("[SUCCESS] insert HttpRuleInput Successfully");
        return true;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean update(HttpRuleInput httpRuleInput) {
        if (!checkServiceId(httpRuleInput)) {
            throw new BaseException(ResultCode.SERVICE_ID_ILLEGAL);
        }
        boolean b = this.updateServiceInfo(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update ServiceInfo");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        b = this.updateHttpRule(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update HttpRule");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        b = this.updateAccessControl(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update AccessControl");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        b = this.updateLoadBalance(httpRuleInput);
        if (!b) {
            logger.error("[FAIL] update LoadBalance");
            throw new BaseException(ResultCode.UPDATE_HTTP_RULE_FAIL);
        }
        logger.info("[SUCCESS] update HttpRuleInput Successfully");
        return true;
    }

    private boolean checkServiceId(HttpRuleInput httpRuleInput) {
        Long id = httpRuleInput.getId();
        return id != null && id != 0;
    }

    private Long saveServiceInfo(HttpRuleInput httpRuleInput) {
        String serviceName = httpRuleInput.getServiceName();
        String serviceDesc = httpRuleInput.getServiceDesc();
        Date date = new Date();
        ServiceInfo info = new ServiceInfo(
                Constant.HTTPLoadType,
                serviceName, serviceDesc,
                date, date, false);
        return serviceDao.saveServiceInfo(info);
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
        int i = accessControlDao.updateAccessControl(accessControl);
        return i > 0;
    }

    private boolean updateLoadBalance(HttpRuleInput httpRuleInput) {
        return false;
    }
}
