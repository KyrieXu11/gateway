package com.kyriexu.controller;

import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.TcpRuleInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/4/19 11:01
 **/
@RestController
@RequestMapping("/service")
public class GrpcController {

    @Autowired
    private TcpController tcpController;

    @PostMapping("/addGrpcService")
    public RespBean addGrpcService(@RequestBody @Validated TcpRuleInput input) {
        return tcpController.addTcpService(input);
    }

    @PutMapping("/updateGrpcService")
    public RespBean updateGrpcService(@RequestBody @Validated TcpRuleInput input) {
        return tcpController.updateTcpService(input);
    }
}
