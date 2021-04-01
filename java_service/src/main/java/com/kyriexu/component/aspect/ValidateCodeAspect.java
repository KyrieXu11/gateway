package com.kyriexu.component.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.controller.CommonController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author KyrieXu
 * @since 2021/4/1 11:37
 **/
@Aspect
@Component
public class ValidateCodeAspect {

    public static final Logger logger = LoggerFactory.getLogger(ValidateCodeAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CommonController commonController;

    @Around("@annotation(com.kyriexu.component.annotation.ValidateCode)")
    public Object handleDelReq(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(Constant.CODE);
        if (StringUtils.isEmpty(code)) {
            return RespBean.error("请输入验证码");
        }
        // 设置 HttpServletRequest 为父子线程共享
        // RequestContextHolder.setRequestAttributes(RequestContextHolder.currentRequestAttributes(), true);
        try {
            code = request.getParameter("code");
            if (StringUtils.isEmpty(code)) {
                return RespBean.error("请输入验证码");
            }
            String c1 = code.toLowerCase();
            String c2 = ((String) session.getAttribute(Constant.CODE)).toLowerCase();
            if (!StringUtils.isEmpty(c1) && c2.equals(c1)) {
                logger.info("[SUCCESS] match code successfully");
            } else {
                logger.info("[FAIL] code is not correct,wrong code is {} correct code is {}", c1, c2);
                // response.setContentType("application/json");
                commonController.verifyCode(response, session);
                return null;
            }
        } catch (IOException e) {
            logger.error("[FAIL] fail to send verify code,cause", e.getCause());
        }
        return joinPoint.proceed();
    }
}
