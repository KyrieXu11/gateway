package com.kyriexu.component.interceptor;

import com.kyriexu.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author KyrieXu
 * @since 2021/1/31 21:27
 **/
@Component
public class IpInterceptor implements HandlerInterceptor {

    /**
     * logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(IpInterceptor.class);


    private AuthService authServiceImpl;

    @Autowired
    public void setAuthServiceImpl(AuthService authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("{} pre start, handler is {}", IpInterceptor.class.getSimpleName(), handler);
        return authServiceImpl.checkAuth(request);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info("{} post start", this.getClass().getSimpleName());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info("{} completion start", this.getClass().getSimpleName());
    }
}
