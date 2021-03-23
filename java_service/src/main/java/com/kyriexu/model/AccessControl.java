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
    private Integer openAuth;
    private String blackList;
    private String whiteList;
    private String whiteHostName;
    private Integer clientIpFlowLimit;
    private Integer serviceFlowLimit;
}
