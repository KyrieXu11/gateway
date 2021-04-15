package com.kyriexu.controller;

import com.kyriexu.common.handler.FlowCounterHandler;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.dto.AdminDto;
import com.kyriexu.dto.PasswordInput;
import com.kyriexu.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author KyrieXu
 * @since 2021/3/23 11:30
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private FlowCounterHandler flowCounterHandler;

    @Autowired
    private AdminService adminService;

    @GetMapping("/info")
    public RespBean info(HttpSession session) {
        Object obj = session.getAttribute(Constant.USER);
        if (Objects.isNull(obj)) {
            return RespBean.error("未登陆");
        }
        AdminDto admin = (AdminDto) obj;
        return RespBean.ok(admin);
    }

    @PostMapping("/login")
    public RespBean login(@RequestBody @Validated AdminDto adminDto, HttpSession session) {
        boolean b = adminService.checkPassword(adminDto);
        if (b) {
            adminDto.setPassword("");
            session.setAttribute(Constant.USER, adminDto);
            logger.info("[LOGIN] :username : {}", adminDto.getUsername());
            return RespBean.ok("登陆成功", adminDto);
        }
        return RespBean.error("登陆失败");
    }

    @PutMapping("/changePass")
    public RespBean changePass(@RequestBody @Validated PasswordInput passwordInput) {
        boolean res = adminService.changePass(passwordInput);
        return res ? RespBean.ok("修改成功") : RespBean.error("修改失败");
    }

    @PostMapping("/logout")
    public RespBean logout(HttpSession session) {
        session.removeAttribute(Constant.USER);
        return RespBean.ok("注销成功");
    }




    // @PostMapping("/register")
    // public RespBean register(@RequestBody @Valid AdminDto adminDto) {
    //     return adminService.register(adminDto) ? RespBean.ok("注册成功") : RespBean.error("注册失败");
    // }

    // @GetMapping("/incr")
    // public RedisFlowCounter incr(@RequestParam @NotNull String appId) {
    //     RedisFlowCounter counter = flowCounterHandler.getCounter(Constant.FLOW_SERVICE_PREFIX + appId);
    //     counter.increase();
    //     return counter;
    // }
}
