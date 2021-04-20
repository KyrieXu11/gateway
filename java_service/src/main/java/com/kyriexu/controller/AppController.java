package com.kyriexu.controller;

import com.kyriexu.annotation.InternalAccess;
import com.kyriexu.annotation.ValidateCode;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.AppListItem;
import com.kyriexu.dto.SearchInput;
import com.kyriexu.dto.ServiceStatOut;
import com.kyriexu.model.App;
import com.kyriexu.model.PageBean;
import com.kyriexu.service.AppService;
import com.kyriexu.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @since 2021/4/12 10:53
 **/
@RestController
@RequestMapping("/app")
@Validated
public class AppController {

    @Autowired
    private StatService statService;

    @Autowired
    private AppService appService;

    @GetMapping("/appStat")
    public RespBean appStat(@RequestParam(value = "id") @NotNull @Min(value = 1, message = "租户ID不能<=0") Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ServiceStatOut serviceStat = statService.getRemoteServiceStat(params);
        return serviceStat != null ? RespBean.ok(serviceStat) : RespBean.error("统计失败");
    }

    @GetMapping("/list")
    public RespBean list(@RequestParam @Min(value = 1, message = "页码最少为1页") int page,
                         @RequestParam @Min(value = 1, message = "页面最少为1条记录") @Max(value = Constant.SIZE_LIMIT, message = "一页最多50条记录") int size,
                         @RequestParam @Nullable String info) {
        SearchInput searchInput = new SearchInput(page, size, info);
        PageBean<AppListItem> pageBean = appService.getAppPageBean(searchInput);
        return RespBean.ok(pageBean);
    }

    @DeleteMapping("/delApp/{id}")
    @ValidateCode(base64 = true)
    public RespBean del(@RequestParam(value = "id") @NotNull @Min(value = 1, message = "租户ID不能<=0") Long id) {
        boolean del = appService.del(id);
        return del ? RespBean.ok("删除成功") : RespBean.error("删除失败");
    }

    @GetMapping("/detail")
    public RespBean detail(@RequestParam(value = "id") @NotNull @Min(value = 1, message = "租户ID不能<=0") Long id) {
        App app = appService.detail(id);
        return app != null ? RespBean.ok(app) : RespBean.error("未找到租户详情");
    }

    @GetMapping("/once/appList")
    @InternalAccess
    public List<App> getAppList() {
        SearchInput input = new SearchInput(Constant.MIN_PAGE, Integer.MAX_VALUE);
        return appService.getAppList(input);
    }

    @PostMapping("/addApp")
    public RespBean addApp(@RequestBody @Validated App app) {
        boolean res = appService.add(app);
        return res ? RespBean.ok("添加成功") : RespBean.error("添加失败");
    }

    @PutMapping("/updateApp")
    public RespBean update(@RequestBody App app){
        boolean res = appService.update(app);
        return res ? RespBean.ok("更新成功"):RespBean.error("更新失败");
    }
}
