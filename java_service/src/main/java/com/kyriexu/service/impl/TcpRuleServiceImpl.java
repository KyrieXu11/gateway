package com.kyriexu.service.impl;

import com.kyriexu.common.utils.Constant;
import com.kyriexu.dao.AccessControlDao;
import com.kyriexu.dao.LoadBalanceDao;
import com.kyriexu.dao.ServiceDao;
import com.kyriexu.dao.TcpRuleDao;
import com.kyriexu.dto.TcpRuleInput;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.model.AccessControl;
import com.kyriexu.model.LoadBalance;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.model.TcpRule;
import com.kyriexu.service.TcpRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/4/12 16:23
 **/
@Service
public class TcpRuleServiceImpl implements TcpRuleService {

    public static final Logger logger = LoggerFactory.getLogger(TcpRuleServiceImpl.class);

    @Autowired
    private TcpRuleDao tcpRuleDao;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private AccessControlDao accessControlDao;

    @Autowired
    private LoadBalanceDao loadBalanceDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean add(TcpRuleInput input) {
        String[] ipList = input.getIpList().split(",");
        String[] weightList = input.getWeightList().split(",");
        if (ipList.length != weightList.length) {
            throw new BaseException(ResultCode.IP_WEIGHT_DIFFERENT);
        }
        long serviceId = saveServiceInfo(input);
        if (serviceId <= 0) {
            logger.error("[FAIL] add service info");
            throw new BaseException(ResultCode.ADD_TCP_RULE_FAILED);
        }
        boolean b = saveTcpRule(input, serviceId);
        if (!b) {
            logger.error("[FAIL] add tcp rule");
            throw new BaseException(ResultCode.ADD_TCP_RULE_FAILED);
        }
        b = saveAccessControl(input, serviceId);
        if (!b) {
            logger.error("[FAIL] add access control");
            throw new BaseException(ResultCode.ADD_TCP_RULE_FAILED);
        }
        b = saveLoadBalance(input, serviceId);
        if (!b) {
            logger.error("[FAIL] add load balance");
            throw new BaseException(ResultCode.ADD_TCP_RULE_FAILED);
        }
        logger.info("[SUCCESS] add tcp service successfully");
        return true;
    }

    private long saveServiceInfo(TcpRuleInput input) {
        ServiceInfo info = new ServiceInfo();
        info.setServiceName(input.getServiceName());
        info.setServiceDesc(input.getServiceDesc());
        info.setLoadType(Constant.TCPLoadType);
        info.setCreateAt(new Date());
        info.setUpdateAt(new Date());
        info.setDeleted(false);
        int i = serviceDao.saveServiceInfo(info);
        if (i == 0) {
            info.setId(0L);
        }
        return info.getId();
    }

    private boolean saveTcpRule(TcpRuleInput input, long serviceId) {
        TcpRule rule = new TcpRule();
        rule.setServiceId(serviceId);
        rule.setPort(input.getPort());
        int rows = tcpRuleDao.add(rule);
        return rows > 0;
    }

    private boolean saveLoadBalance(TcpRuleInput input, long serviceId) {
        LoadBalance loadBalance = new LoadBalance();
        loadBalance.setServiceId(serviceId);
        loadBalance.setIpList(input.getIpList());
        loadBalance.setForbidList(input.getForbidList());
        loadBalance.setRoundType(input.getRoundType());
        loadBalance.setWeightList(input.getWeightList());
        int rows = loadBalanceDao.save(loadBalance);
        return rows > 0;
    }

    private boolean saveAccessControl(TcpRuleInput input, long serviceId) {
        AccessControl accessControl = new AccessControl();
        accessControl.setServiceId(serviceId);
        accessControl.setBlackList(input.getBlackList());
        accessControl.setClientIpFlowLimit(input.getClientipFlowLimit());
        accessControl.setServiceFlowLimit(input.getServiceFlowLimit());
        accessControl.setOpenAuth(input.getOpenAuth());
        accessControl.setWhiteHostName(input.getWhiteHostName());
        accessControl.setWhiteList(input.getWhiteList());
        int rows = accessControlDao.save(accessControl);
        return rows > 0;
    }
}
