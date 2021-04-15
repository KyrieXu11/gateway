package com.kyriexu.aspect;

import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
import com.kyriexu.service.SubmitService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/4/1 19:43
 **/
@Aspect
@Component
public class SingleSubmitAspect {

    public static final Logger logger = LoggerFactory.getLogger(SingleSubmitAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SubmitService submitService;

    @Around("@annotation(com.kyriexu.annotation.SingleSubmit)")
    public Object singleSubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        String reqToken = request.getHeader(Constant.X_GATEWAY_TOKEN);
        if (StringUtils.isEmpty(reqToken)) {
            logger.info("[FAIL] request : {} has no token", request.getRequestURI());
            return RespBean.error("Illegal Operation");
        }
        if (!reqToken.matches(Constant.UUID_REGEX)) {
            logger.info("[FAIL] token : {} is illegal", reqToken);
            return RespBean.error("Illegal Token");
        }
        if (submitService.isTokenExist(reqToken)) {
            logger.info("[FAIL] token : {} is already exist", reqToken);
            submitService.removeToken(reqToken);
            return RespBean.error("Don't Submit Form Twice");
        } else {
            logger.info("[SUCCESS] saved token : {}", reqToken);
            submitService.saveToken(reqToken);
            return joinPoint.proceed();
        }
    }
}
