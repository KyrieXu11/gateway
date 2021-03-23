package com.kyriexu.controller;

import com.kyriexu.dto.AdminDto;
import com.kyriexu.dto.PasswordInput;
import com.kyriexu.service.AdminService;
import com.kyriexu.utils.Constant;
import com.kyriexu.utils.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    public RespBean login(@RequestBody AdminDto adminDto, HttpSession session) {
        boolean b = adminService.checkPassword(adminDto);
        if (b) {
            adminDto.setPassword("");
            session.setAttribute(Constant.USER, adminDto);
            logger.info("{}", adminDto);
            return RespBean.ok("登陆成功");
        }
        return RespBean.error("登陆失败");
    }

    @PutMapping("/changepass")
    public RespBean changePass(@RequestBody @Valid PasswordInput passwordInput) {
        boolean res = adminService.changePass(passwordInput);
        return res ? RespBean.ok("修改成功") : RespBean.error("修改失败");
    }

    @PostMapping("/logout")
    public RespBean logout(HttpSession session) {
        session.removeAttribute(Constant.USER);
        return RespBean.ok("注销成功");
    }

    @PostMapping("/register")
    public RespBean register(@RequestBody @Valid AdminDto adminDto) {
        return adminService.register(adminDto) ? RespBean.ok("注册成功") : RespBean.error("注册失败");
    }
}
