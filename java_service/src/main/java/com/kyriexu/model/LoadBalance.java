package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadBalance {
    private Long id;
    private Long serviceId;
    private int checkMethod;
    private int checkTimeout;
    private int checkInterval;
    private int roundType;
    private String ipList;
    private String weightList;
    private String forbidList;
    private int upstreamConnectTimeout;
    private int upstreamHeaderTimeout;
    private int upstreamIdleTimeout;
    private int upstreamMaxIdle;

    public LoadBalance(Long serviceId, int roundType, String ipList, String weightList, int upstreamConnectTimeout, int upstreamHeaderTimeout, int upstreamIdleTimeout, int upstreamMaxIdle) {
        this.serviceId = serviceId;
        this.roundType = roundType;
        this.ipList = ipList;
        this.weightList = weightList;
        this.upstreamConnectTimeout = upstreamConnectTimeout;
        this.upstreamHeaderTimeout = upstreamHeaderTimeout;
        this.upstreamIdleTimeout = upstreamIdleTimeout;
        this.upstreamMaxIdle = upstreamMaxIdle;
    }

    public List<String> getIPListByModel() {
        return Arrays.asList(this.ipList.split(","));
    }

    public List<String> getWeightListByModel() {
        return Arrays.asList(this.weightList.split(","));
    }
}
