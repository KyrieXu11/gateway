package com.kyriexu.service.impl;

import com.kyriexu.common.consul.ConsulClient;
import com.kyriexu.common.utils.ComponentUtils;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RestTemplateHttpUtils;
import com.kyriexu.dto.PanelGroupDataOutput;
import com.kyriexu.dto.ServiceStatOut;
import com.kyriexu.service.StatService;
import com.orbitz.consul.model.health.ServiceHealth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/4/10 22:10
 **/
@Service
public class StatServiceImpl implements StatService {

    public static final Logger logger = LoggerFactory.getLogger(StatServiceImpl.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ComponentUtils componentUtils;

    @Autowired
    private RestTemplateHttpUtils restTemplateHttpUtils;

    @Override
    public PanelGroupDataOutput getRemotePanelGroupData(boolean isConsul) {
        String url;
        String uri = request.getRequestURI();
        logger.info("call api {}", uri);
        if (isConsul) {
            ConsulClient client = new ConsulClient(componentUtils.getConsulAddr());
            List<ServiceHealth> serviceList = client.findHealthyService(Constant.GO_SERVICE);
            // TODO: 做负载均衡
            ServiceHealth serviceHealth = serviceList.get(0);
            int port = serviceHealth.getService().getPort();
            String address = serviceHealth.getService().getAddress();
            url = String.format("http://%s:%d/%s", address, port, uri);
        } else {
            url = String.format("http://%s/%s", componentUtils.getGoServiceAddr(), uri);
        }
        logger.info("[INTERNAL CALL] url: {}", url);
        return restTemplateHttpUtils.getInternalGenericObject(url, null, PanelGroupDataOutput.class);
    }

    @Override
    public <V> ServiceStatOut getRemoteServiceStat(Map<String, V> params) {
        String uri = request.getRequestURI();
        String url = String.format("http://%s/%s", componentUtils.getGoServiceAddr(), uri);
        logger.info("[INTERNAL CALL] url: {}", url);
        return restTemplateHttpUtils.getInternalGenericObject(url, params, ServiceStatOut.class);
    }
}
