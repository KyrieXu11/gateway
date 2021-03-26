package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/3/26 10:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    private long id;
    private String appId;
    private String name;
    private String secret;
    private String whiteIps;
    private long qpd;
    private long qps;
    private Date createAt;
    private Date updateAt;
    private boolean deleted;
}
