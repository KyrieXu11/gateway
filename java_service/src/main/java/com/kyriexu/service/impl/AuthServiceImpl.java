package com.kyriexu.service.impl;

import com.kyriexu.component.config.WhiteListConfig;
import com.kyriexu.dto.AdminDto;
import com.kyriexu.rpc.matchrpc.GoRequest;
import com.kyriexu.service.adapter.AuthServiceAdapter;
import com.kyriexu.common.utils.Constant;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/1/30 13:23
 **/
@Service
public class AuthServiceImpl extends AuthServiceAdapter {

    /**
     * logger
     */
    public static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final PathMatcher matcher = new AntPathMatcher();
    /**
     * white list config bean.
     */
    @Autowired
    private WhiteListConfig whiteListConfig;

    /**
     * see {@link com.kyriexu.service.AuthService#checkAuth(GoRequest)}
     *
     * @param req gRpc request
     * @return auth result
     */
    @Override
    public boolean checkAuth(GoRequest req) {
        // 由于只是统计方法执行时间，所以不在乎是否是线程安全的
        long l = System.currentTimeMillis();
        logger.info("start time -> :{}", l);
        String method = req.getMethod();
        // TODO: IP 地址的校验
        String addr = req.getRemoteAddr();
        String path = req.getRealPath();
        boolean res = check(path, method);
        long end = System.currentTimeMillis();
        logger.info("end time -> :{}", end);
        logger.info("use time -> {}", end - l);
        return res;
    }

    /**
     * see {@link com.kyriexu.service.AuthService#checkAuth(HttpServletRequest)}
     *
     * @param request servlet request
     * @return auth result
     */
    @Override
    public boolean checkAuth(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Object user = request.getSession().getAttribute(Constant.USER);
        if (null == user) {
            return check(uri, method);
        }
        AdminDto admin = (AdminDto) user;
        return Strings.isNotBlank(admin.getUsername());
    }

    private boolean check(String path, String method) {
        boolean res = false;
        method = method.toLowerCase();
        Map<String, List<String>> whiteList = whiteListConfig.getUrls();
        List<String> urls = whiteList.get(method);
        for (String url : urls) {
            if (matcher.match(url, path)) {
                logger.info("[SUCCESS] : pattern -> {} realpath -> {}", url, path);
                res = true;
                break;
            }
            logger.info("[FAILED] : pattern -> {} realpath -> {} ", url, path);
        }
        return res;
    }
}
