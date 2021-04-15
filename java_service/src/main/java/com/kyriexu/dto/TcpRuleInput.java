package com.kyriexu.dto;

import com.kyriexu.annotation.validation.HeaderTransFor;
import com.kyriexu.annotation.validation.IpList;
import com.kyriexu.common.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author KyrieXu
 * @since 2021/4/12 15:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcpRuleInput {
    private Long id;

    @NotNull(message = "服务名称不能为空")
    private String serviceName;

    @NotNull(message = "服务描述不能为空")
    @Size(min = Constant.SERVICE_DESC_MIN, max = Constant.SERVICE_DESC_MAX)
    private String ServiceDesc;

    @NotNull
    @Min(value = 8001, message = "端口最小不能小于8001")
    @Max(value = 8999, message = "端口最大不能大于8999")
    private Integer port;

    @HeaderTransFor
    private String headerTransfor;

    @Min(value = 0, message = "OpenAuth 最小不能小于0")
    @Max(value = 1, message = "OpenAuth 最大不能超过1")
    private int openAuth;

    @IpList(message = "黑名单格式不正确")
    private String blackList;
    @IpList(message = "白名单格式不正确")
    private String whiteList;

    @Min(value = 0, message = "ServiceFlowLimit 最小不能小于0")
    private int clientipFlowLimit;
    @Min(value = 0, message = "ServiceFlowLimit 最小不能小于0")
    private int serviceFlowLimit;

    @IpList(message = "白名单主机名称格式不正确")
    private String whiteHostName;

    @Min(value = 0, message = "RoundType 最小不能小于0")
    @Max(value = 3, message = "RoundType 最大不能超过3")
    private int roundType;

    @IpList(message = "ip列表格式不正确")
    private String ipList;
    private String weightList;

    @IpList(message = "禁用ip列表格式不正确")
    private String forbidList;

    public boolean checkId() {
        return id != null && id != 0;
    }
}
