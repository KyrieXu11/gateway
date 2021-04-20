package com.kyriexu.controller;

import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.TcpRuleInput;
import com.kyriexu.service.TcpRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/4/12 15:19
 **/
@RestController
@RequestMapping("/service")
public class TcpController {

    @Autowired
    private TcpRuleService tcpRuleService;

    @PostMapping("/addTcpService")
    public RespBean addTcpService(@RequestBody @Validated TcpRuleInput tcpRuleInput) {
        boolean res = tcpRuleService.add(tcpRuleInput);
        return res ? RespBean.ok("添加 tcp 服务成功") : RespBean.error("添加 tcp 服务失败");
    }

    @PutMapping("/updateTcpService")
    public RespBean updateTcpService(@RequestBody @Validated TcpRuleInput tcpRuleInput) {
        if (tcpRuleService.update(tcpRuleInput)) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
