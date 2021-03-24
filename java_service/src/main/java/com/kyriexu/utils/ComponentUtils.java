package com.kyriexu.utils;

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

    public int getClusterSSLPort() {
        return clusterSSLPort;
    }

    public void setClusterSSLPort(int clusterSSLPort) {
        this.clusterSSLPort = clusterSSLPort;
    }

    public String getClusterIp() {
        return clusterIp;
    }

    public void setClusterIp(String clusterIp) {
        this.clusterIp = clusterIp;
    }

    public int getClusterPort() {
        return clusterPort;
    }

    public void setClusterPort(int clusterPort) {
        this.clusterPort = clusterPort;
    }
}
