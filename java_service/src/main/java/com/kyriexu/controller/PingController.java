package com.kyriexu.controller;

import com.kyriexu.common.utils.RespBean;
import com.kyriexu.annotation.InternalAccess;
import com.kyriexu.annotation.ValidateCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/1/31 21:36
 **/
@RestController
public class PingController {

    @GetMapping("/hello")
    public String index() {
        return "hello";
    }

    @DeleteMapping("/d/{id}")
    @ValidateCode
    public RespBean del(@PathVariable Integer id) {
        return RespBean.ok(id.toString());
    }

    @GetMapping("/s")
    @InternalAccess
    public RespBean submit() {
        return RespBean.ok("123");
    }
}
