package com.kyriexu.service.impl;

import com.kyriexu.component.config.WhiteListConfig;
import com.kyriexu.rpc.matchrpc.GoRequest;
import com.kyriexu.service.adapter.AuthServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    /**
     * white list config bean.
     */
    private WhiteListConfig whiteListConfig;

    @Autowired
    public void setWhiteListConfig(WhiteListConfig whiteListConfig) {
        this.whiteListConfig = whiteListConfig;
    }

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
        LOGGER.info("start time -> :{}", l);

        boolean res = false;
        PathMatcher matcher = new AntPathMatcher();

        Map<String, List<String>> whiteList = whiteListConfig.getUrls();

        String method = req.getMethod().toLowerCase();
        // TODO: IP 地址的校验
        String addr = req.getRemoteAddr();
        String path = req.getRealPath();

        List<String> urls = whiteList.get(method);
        for (String url : urls) {
            if (matcher.match(url, path)) {
                res = true;
                break;
            }
            // 匹配成功则不会输出，只输出匹配失败的 url
            LOGGER.info("pattern -> {} realpath -> {}", url, path);
        }

        long end = System.currentTimeMillis();
        LOGGER.info("end time -> :{}", end);
        LOGGER.info("use time -> {}", end - l);
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
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String header = request.getHeader(name);
            LOGGER.info("{} is {}", name, header);
        }
        return super.checkAuth(request);
    }
}
