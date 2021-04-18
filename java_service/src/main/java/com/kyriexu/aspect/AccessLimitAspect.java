package com.kyriexu.aspect;

import com.kyriexu.annotation.InternalAccess;
import com.kyriexu.common.utils.RespBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/7 13:33
 **/
@Component
@Aspect
public class AccessLimitAspect {

    public static final Logger logger = LoggerFactory.getLogger(AccessLimitAspect.class);

    @Value("${access.ip}")
    private List<String> internIps;

    @Value("${dev}")
    private boolean dev;

    @Autowired
    private HttpServletRequest request;

    @Around("(execution(public * *(..)) && @within(internalAccess)) || @annotation(internalAccess)")
    public Object internalAccess(ProceedingJoinPoint joinPoint, InternalAccess internalAccess) throws Throwable {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        if (StringUtils.isEmpty(ip)) {
            logger.info("[FAIL] empty ip call");
            return RespBean.error("IP都没有还想调用呢？");
        }
        // 优先读取配置文件当中的内网ip
        if (CollectionUtils.isEmpty(internIps)) {
            if (Arrays.stream(internalAccess.value()).noneMatch(ip::matches)) {
                logger.info("[FAIL] call failed");
                return RespBean.error("别搁这调用了");
            }
        } else {
            if (internIps.stream().noneMatch(ip::matches)) {
                logger.info("[FAIL] call failed");
                return RespBean.error("别搁这调用了");
            }
        }
        logger.info("ip: {} call the api {}", ip, uri);
        return joinPoint.proceed();
    }

    @Around("execution(public * com.kyriexu.service.impl.AuthServiceImpl.checkAuth(javax.servlet.http.HttpServletRequest))")
    public Object devAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        if (dev) {
            return true;
        }
        return joinPoint.proceed();
    }
}
