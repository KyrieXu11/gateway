package com.kyriexu.controller;

import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.PanelGroupDataOutput;
import com.kyriexu.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/3/26 10:43
 **/
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/panelGroupData")
    public RespBean getPanelGroupData(HttpServletRequest request) {
        PanelGroupDataOutput panelGroupData = dashBoardService.getRemotePanelGroupData(request, false);
        return RespBean.ok(panelGroupData);
    }
}
