package com.kyriexu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.common.utils.Utils;
import com.kyriexu.common.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/verifyCode")
    public void verifyCode(@RequestParam(value = "base64",defaultValue = "1") boolean isBase64, HttpServletResponse response, HttpSession session) throws IOException {
        VerifyCode code;
        if (!isBase64) {
            code = new VerifyCode();
            VerifyCode.output(code, response.getOutputStream());
        } else {
            code = new VerifyCode();
            String base64 = code.getBase64();
            response.setContentType(Constant.ContentTypeJson);
            Utils.output(response.getOutputStream(), objectMapper, RespBean.ok("", base64));
        }
        session.setAttribute(Constant.CODE, code.getText());
    }
}
