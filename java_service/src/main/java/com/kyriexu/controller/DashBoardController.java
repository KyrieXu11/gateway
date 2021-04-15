package com.kyriexu.controller;

import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.DashServiceStatOutput;
import com.kyriexu.dto.PanelGroupDataOutput;
import com.kyriexu.dto.ServiceStatOut;
import com.kyriexu.service.DashBoardService;
import com.kyriexu.service.ServiceService;
import com.kyriexu.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/3/26 10:43
 **/
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private StatService statService;

    @GetMapping("/panelGroupData")
    public RespBean getPanelGroupData() {
        PanelGroupDataOutput panelGroupData = statService.getRemotePanelGroupData(false);
        return panelGroupData != null ? RespBean.ok(panelGroupData) : RespBean.error("no data");
    }

    @GetMapping("/serviceStat")
    public RespBean getServiceStat() {
        DashServiceStatOutput out = serviceService.countByLoadType();
        return RespBean.ok(out);
    }

    @GetMapping("/flowStat")
    public RespBean getFlowStat() {
        ServiceStatOut serviceStat = statService.getRemoteServiceStat(null);
        return serviceStat != null ? RespBean.ok(serviceStat) : RespBean.error("统计失败");
    }

}
