package com.kyriexu.controller;

import com.kyriexu.service.ServiceService;
import com.kyriexu.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:09
 **/
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/list")
    public RespBean list(@RequestParam int page, @RequestParam int size, @RequestParam String info) {
        serviceService.getPageBean(page, size,info);
        return null;
    }
}
