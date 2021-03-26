package com.kyriexu.controller;

import com.kyriexu.dto.ServiceInput;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.service.ServiceService;
import com.kyriexu.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/26 10:43
 **/
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/panelGroupData")
    public RespBean getPanelGroupData() {
        ServiceInput serviceInput = new ServiceInput(1, 1);
        List<ServiceInfo> serviceInfoList = serviceService.getServiceInfoList(serviceInput);
        return RespBean.ok(serviceInfoList);
    }
}
