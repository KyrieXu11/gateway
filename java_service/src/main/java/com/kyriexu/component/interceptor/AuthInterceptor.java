package com.kyriexu.component.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.common.utils.Utils;
import com.kyriexu.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author KyrieXu
 * @since 2021/1/31 21:27
 **/
@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private AuthService authServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean res = authServiceImpl.checkAuth(request);
        if (!res) {
            response.setHeader("content-type","application/json");
            response.setCharacterEncoding("UTF-8");
            Utils.output(response.getOutputStream(),objectMapper,RespBean.error("没有访问权限或者未登陆"));
        }
        return res;
    }
}
