package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadBalance {
    private Long id;
    private Long serviceId;
    private Integer checkMethod;
    private Integer checkTimeout;
    private Integer checkInterval;
    private Integer roundType;
    private String ipList;
    private String weightList;
    private String forbidList;
    private Integer UpstreamConnectTimeout;
    private Integer UpstreamHeaderTimeout;
    private Integer UpstreamIdleTimeout;
    private Integer UpstreamMaxIdle;

    public List<String> getIPListByModel() {
        return Arrays.asList(this.ipList.split(","));
    }

    public List<String> getWeightListByModel() {
        return Arrays.asList(this.weightList.split(","));
    }
}
