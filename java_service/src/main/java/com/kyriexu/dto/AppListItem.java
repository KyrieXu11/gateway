package com.kyriexu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/4/14 13:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppListItem {
    private Long id;
    private Long appId;
    private String name;
    private String secret;
    private String whiteIps;
    private Long qpd;
    private Long qps;
    private Long readQps;
    private Long readQpd;
    private Date updateAt;
    private Date createAt;
    private boolean deleted;
}
