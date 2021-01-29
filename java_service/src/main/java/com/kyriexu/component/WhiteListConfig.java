package com.kyriexu.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author KyrieXu
 * @since 2021/1/30 1:21
 **/
@Configuration
@ConfigurationProperties(prefix = "admin")
public class WhiteListConfig {
    /**
     * 请求方式为 get 的白名单
     * key 是 请求的方式
     * value 则是 白名单的 url
     */
    private Map<String, List<String>> urls = new ConcurrentHashMap<>();

    public Map<String, List<String>> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, List<String>> map) {
        this.urls = map;
    }

}
