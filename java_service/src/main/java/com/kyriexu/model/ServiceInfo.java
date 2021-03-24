package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo {
    private Long id;
    private Integer loadType;
    private String serviceName;
    private String serviceDesc;
    private Date createAt;
    private Date updateAt;
    private boolean deleted;

    public ServiceInfo(Integer loadType, String serviceName, String serviceDesc, Date createAt, Date updateAt, boolean deleted) {
        this.loadType = loadType;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleted = deleted;
    }
}
