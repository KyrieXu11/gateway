package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessControl {
    private Long id;
    private Long serviceId;
    private int openAuth;
    private String blackList;
    private String whiteList;
    private String whiteHostName;
    private int clientIpFlowLimit;
    private int serviceFlowLimit;

    public AccessControl(Long serviceId, int openAuth, String blackList, String whiteList, int clientIpFlowLimit, int serviceFlowLimit) {
        this.serviceId = serviceId;
        this.openAuth = openAuth;
        this.blackList = blackList;
        this.whiteList = whiteList;
        this.clientIpFlowLimit = clientIpFlowLimit;
        this.serviceFlowLimit = serviceFlowLimit;
    }

    public AccessControl(Long serviceId, int openAuth, String blackList, String whiteList, String whiteHostName, int clientIpFlowLimit, int serviceFlowLimit) {
        this.serviceId = serviceId;
        this.openAuth = openAuth;
        this.blackList = blackList;
        this.whiteList = whiteList;
        this.whiteHostName = whiteHostName;
        this.clientIpFlowLimit = clientIpFlowLimit;
        this.serviceFlowLimit = serviceFlowLimit;
    }
}
