package com.kyriexu.controller;

import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.VerifyCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author KyrieXu
 * @since 2021/4/1 11:59
 **/
@RestController
public class CommonController {
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        VerifyCode code = new VerifyCode();
        VerifyCode.output(code, response.getOutputStream());
        session.setAttribute(Constant.CODE, code.getText());
    }
}
