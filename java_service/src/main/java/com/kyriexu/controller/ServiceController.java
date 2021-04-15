package com.kyriexu.controller;

import com.kyriexu.annotation.InternalAccess;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.SearchInput;
import com.kyriexu.dto.ServiceStatOut;
import com.kyriexu.model.ServiceDetail;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.model.PageBean;
import com.kyriexu.dto.ServiceListItem;
import com.kyriexu.service.ServiceService;
import com.kyriexu.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private StatService statService;

    @GetMapping("/list")
    public RespBean list(@RequestParam @Min(value = 1, message = "页码最少为1页") int page,
                         @RequestParam @Min(value = 1, message = "页面最少为1条记录") @Max(value = Constant.SIZE_LIMIT, message = "一页最多50条记录") int size,
                         @RequestParam @Nullable String info) {
        SearchInput searchInput = new SearchInput(page, size, info);
        PageBean<ServiceListItem> pageBean = serviceService.getPageBean(searchInput);
        return RespBean.ok(pageBean);
    }

    @GetMapping("/detail")
    public RespBean detail(@RequestParam("service_id") @NotNull @Min(value = 1, message = "服务ID不能<=0") Long serviceId) {
        ServiceInfo info = new ServiceInfo();
        info.setId(serviceId);
        ServiceDetail detail = serviceService.getServiceDetail(info);
        return RespBean.ok(detail);
    }

    @GetMapping("/once/details")
    @InternalAccess
    public List<ServiceDetail> serviceDetails() {
        SearchInput input = new SearchInput(Constant.MIN_PAGE, Integer.MAX_VALUE);
        return serviceService.getServiceDetails(input);
    }

    @GetMapping("/serviceStat")
    public RespBean serviceStat(@RequestParam("service_id") @NotNull @Min(value = 1, message = "服务ID不能<=0") Long serviceId) {
        Map<String, Long> params = new HashMap<>();
        params.put("service_id", serviceId);
        ServiceStatOut serviceStat = statService.getRemoteServiceStat(params);
        return serviceStat != null ? RespBean.ok(serviceStat) : RespBean.error("统计失败");
    }

    @DeleteMapping("/del/{id}")
    public RespBean delService(@PathVariable(value = "id") @NotNull @Min(value = 1, message = "服务ID不能<=0") Long serviceId) {
        boolean res = serviceService.del(serviceId);
        return res ? RespBean.ok("删除成功") : RespBean.error("删除失败");
    }

}
