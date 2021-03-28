package com.kyriexu.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author KyrieXu
 * @since 2021/3/24 15:15
 **/
@Component
public class ComponentUtils {

    @Value("${cluster.clusterIp}")
    private String clusterIp;

    @Value("${cluster.clusterPort}")
    private int clusterPort;

    @Value("${cluster.clusterSSLPort}")
    private int clusterSSLPort;

    @Value("${consul.addr}")
    private String consulAddr;

    @Value("${go-service.host}")
    private String goServiceHost;

    public String getConsulAddr() {
        return consulAddr;
    }

    public String getGoServiceHost() {
        return goServiceHost;
    }

    public int getClusterSSLPort() {
        return clusterSSLPort;
    }

    public String getClusterIp() {
        return clusterIp;
    }

    public int getClusterPort() {
        return clusterPort;
    }
}
