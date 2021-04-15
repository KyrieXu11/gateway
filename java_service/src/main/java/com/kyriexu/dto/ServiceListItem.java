package com.kyriexu.dto;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:19
 **/
public class ServiceListItem {
    private Long id;
    private String serviceName;
    private String serviceDesc;
    private Integer loadType;
    private String serviceAddr;
    private Long qps;
    private Long qpd;
    private Integer totalNode;

    public ServiceListItem() {
    }

    public ServiceListItem(Long id, String serviceName, String serviceDesc, Integer loadType, String serviceAddr, Long qps, Long qpd, Integer totalNode) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.loadType = loadType;
        this.serviceAddr = serviceAddr;
        this.qps = qps;
        this.qpd = qpd;
        this.totalNode = totalNode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public Integer getLoadType() {
        return loadType;
    }

    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public Long getQps() {
        return qps;
    }

    public void setQps(Long qps) {
        this.qps = qps;
    }

    public Long getQpd() {
        return qpd;
    }

    public void setQpd(Long qpd) {
        this.qpd = qpd;
    }

    public Integer getTotalNode() {
        return totalNode;
    }

    public void setTotalNode(Integer totalNode) {
        this.totalNode = totalNode;
    }
}
