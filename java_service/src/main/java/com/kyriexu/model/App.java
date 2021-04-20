package com.kyriexu.model;

import com.kyriexu.annotation.validation.IpList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/3/26 10:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    private Long id;
    @NotNull
    private String appId;
    @NotNull
    private String name;
    private String secret;
    @IpList(message = "白名单格式不正确")
    private String whiteIps;
    private long qpd;
    private long qps;
    private Date createAt;
    private Date updateAt;
    private boolean deleted;
}
