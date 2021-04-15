package com.kyriexu.service.impl;

import com.kyriexu.common.handler.FlowCounterHandler;
import com.kyriexu.common.handler.RedisFlowCounter;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.dto.PanelGroupDataOutput;
import com.kyriexu.service.DashBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author KyrieXu
 * @since 2021/3/27 14:07
 **/
@Service
public class DashBoardServiceImpl implements DashBoardService {
    public static final Logger logger = LoggerFactory.getLogger(DashBoardServiceImpl.class);

    @Autowired
    private FlowCounterHandler handler;

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
