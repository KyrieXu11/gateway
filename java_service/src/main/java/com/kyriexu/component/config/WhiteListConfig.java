package com.kyriexu.component.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author KyrieXu
 * @since 2021/1/30 1:21
 **/
@Configuration
@ConfigurationProperties(prefix = "admin")
public class WhiteListConfig {
    /**
     * key is request method
     * value is whitelist uri
     * <p>
     * use hashmap instead of ConcurrentHashMap.
     * there is some reason following:
     * 1. all of thread don't change map's key or value,they just read kv from map.
     * 2. gRpc use multi-thread to touch request.
     */
    private Map<String, List<String>> urls = new HashMap<>();

    public Map<String, List<String>> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, List<String>> map) {
        this.urls = map;
    }
}
