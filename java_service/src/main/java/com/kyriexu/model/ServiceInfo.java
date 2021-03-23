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
    private Boolean deleted;
}
