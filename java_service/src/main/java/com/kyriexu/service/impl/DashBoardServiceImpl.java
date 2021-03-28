package com.kyriexu.service.impl;

import com.kyriexu.common.handler.FlowCounterHandler;
import com.kyriexu.common.handler.RedisFlowCounter;
import com.kyriexu.common.utils.ComponentUtils;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.component.consul.ConsulClient;
import com.kyriexu.dto.PanelGroupDataOutput;
import com.kyriexu.service.DashBoardService;
import com.orbitz.consul.model.health.ServiceHealth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/27 14:07
 **/
@Service
public class DashBoardServiceImpl implements DashBoardService {
    public static final Logger logger = LoggerFactory.getLogger(DashBoardServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ComponentUtils componentUtils;

    @Autowired
    private FlowCounterHandler handler;

    @Override
    public PanelGroupDataOutput getRemotePanelGroupData(HttpServletRequest request, boolean isConsul) {
        String url;
        String uri = request.getRequestURI();
        if (isConsul) {
            ConsulClient client = new ConsulClient(componentUtils.getConsulAddr());
            List<ServiceHealth> serviceList = client.findHealthyService(Constant.GO_SERVICE);
            // TODO: 做负载均衡
            ServiceHealth serviceHealth = serviceList.get(0);
            int port = serviceHealth.getService().getPort();
            String address = serviceHealth.getService().getAddress();
            url = String.format("http://%s:%d/%s", address, port, uri);
        } else {
            url = String.format("http://%s/%s", componentUtils.getGoServiceHost(), uri);
        }
        ResponseEntity<PanelGroupDataOutput> entity = restTemplate.getForEntity(url, PanelGroupDataOutput.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        return null;
    }

    @Override
    public PanelGroupDataOutput getLocalPanelGroupData() {
        RedisFlowCounter counter = handler.getCounter(Constant.FLOW_TOTAL);
        PanelGroupDataOutput output = new PanelGroupDataOutput(
                0,
                0,
                counter.getTotalCount().longValue(),
                counter.getQps()
        );
        return null;
    }
}
