package com.kyriexu.component.aspect;

import com.kyriexu.common.utils.Constant;
import com.kyriexu.common.utils.RespBean;
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

    @Around("@annotation(com.kyriexu.component.annotation.SingleSubmit)")
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
        if (isTokenExist(reqToken)) {
            logger.info("[FAIL] token : {} is already exist", reqToken);
            removeToken(reqToken);
            return RespBean.error("Token Already Exist");
        } else {
            logger.info("[SUCCESS] saved token : {}", reqToken);
            saveToken(reqToken);
            return joinPoint.proceed();
        }
    }

    private void removeToken(String token) {
    }

    private void saveToken(String token) {
    }

    private boolean isTokenExist(String token) {
        return false;
    }


}
