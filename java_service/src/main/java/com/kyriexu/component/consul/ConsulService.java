package com.kyriexu.component.consul;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/3/27 11:31
 **/
@Data
public class ConsulService {
    private String serviceId;
    private String address;
    private String name;
    private int port;
    private long checkInterval;
    private List<String> tags;
    private Map<String, String> meta;

    public ConsulService(String serviceId, String name, int port, long checkInterval, List<String> tags) {
        this.serviceId = serviceId;
        this.address = "127.0.0.1";
        this.name = name;
        this.port = port;
        this.checkInterval = checkInterval;
        this.tags = tags;
        this.meta = null;
    }

    public ConsulService(String serviceId, String name, int port, long checkInterval, List<String> tags, Map<String, String> meta) {
        this.serviceId = serviceId;
        this.address = "127.0.0.1";
        this.name = name;
        this.port = port;
        this.checkInterval = checkInterval;
        this.tags = tags;
        this.meta = meta;
    }

    public ConsulService(String serviceId, String address, String name, int port, long checkInterval, List<String> tags, Map<String, String> meta) {
        this.serviceId = serviceId;
        this.address = address;
        this.name = name;
        this.port = port;
        this.checkInterval = checkInterval;
        this.tags = tags;
        this.meta = meta;
    }
}
