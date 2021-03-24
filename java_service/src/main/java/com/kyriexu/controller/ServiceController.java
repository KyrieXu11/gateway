package com.kyriexu.controller;

import com.kyriexu.dto.ServiceInput;
import com.kyriexu.dto.ServiceSearch;
import com.kyriexu.model.ServiceDetail;
import com.kyriexu.model.ServicePageBean;
import com.kyriexu.service.ServiceService;
import com.kyriexu.utils.Constant;
import com.kyriexu.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:09
 **/
@RestController
@RequestMapping("/service")
@Validated
public class ServiceController {


    @Autowired
    private ServiceService serviceService;

    @GetMapping("/list")
    public RespBean list(@RequestParam @Min(value = 1, message = "页码最少为1页") int page,
                         @RequestParam @Min(value = 1, message = "页面最少为1条记录") @Max(value = Constant.SIZE_LIMIT, message = "一页最多50条记录") int size,
                         @RequestParam @Nullable String info) {
        ServiceInput serviceInput = new ServiceInput(page, size, info);
        ServicePageBean pageBean = serviceService.getPageBean(serviceInput);
        return RespBean.ok(pageBean);
    }

    @GetMapping("/detail")
    public RespBean detail(@RequestParam("service_id") @NotNull Long serviceId) {
        ServiceSearch search = new ServiceSearch(serviceId, Constant.SERVICE_ALL);
        ServiceDetail detail = serviceService.getServiceDetail(search);
        return RespBean.ok(detail);
    }
}
