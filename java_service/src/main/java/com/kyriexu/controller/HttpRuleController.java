package com.kyriexu.controller;

import com.kyriexu.dto.HttpRuleInput;
import com.kyriexu.service.HttpRuleService;
import com.kyriexu.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/3/24 19:48
 **/
@RestController
@RequestMapping("/service")
@Validated
public class HttpRuleController {

    @Autowired
    private HttpRuleService httpRuleService;

    @PostMapping("/addHttpRule")
    public RespBean addHttpRule(@RequestBody HttpRuleInput httpRuleInput) {
        boolean res = httpRuleService.add(httpRuleInput);
        return res ? RespBean.ok("添加成功") : RespBean.error("添加失败");
    }

    @PutMapping("/updateHttpRule")
    public RespBean updateHttpRule(@RequestBody HttpRuleInput httpRuleInput) {
        boolean res = httpRuleService.update(httpRuleInput);
        return res ? RespBean.ok("更新成功") : RespBean.error("更新失败");
    }
}
