package com.kyriexu.dto;

import com.kyriexu.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author KyrieXu
 * @since 2021/3/24 18:13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpRuleInput {

    /**
     * 更新时需要用到id,插入不需要,所以在更新的时要手动校验ID
     */
    private Long id;

    @NotNull(message = "服务名称不能为空")
    private String serviceName;

    @NotNull(message = "服务描述不能为空")
    @Size(min = Constant.SERVICE_DESC_MIN, max = Constant.SERVICE_DESC_MAX)
    private String ServiceDesc;

    @Min(value = 0, message = "ruleType 最小不能小于0")
    @Max(value = 1, message = "ruleType 最大不能超过1")
    private int ruleType;

    @Pattern(regexp = "^\\S+$", message = "规则必须有空格分开")
    private String rule;

    @Min(value = 0, message = "needHttps 最小不能小于0")
    @Max(value = 1, message = "needHttps 最大不能超过1")
    private int needHttps;

    @Min(value = 0, message = "needStripUri 最小不能小于0")
    @Max(value = 1, message = "needStripUri 最大不能超过1")
    private int needStripUri;

    @Min(value = 0, message = "needWebSocket 最小不能小于0")
    @Max(value = 1, message = "needWebSocket 最大不能超过1")
    private int needWebSocket;

    private String urlRewrite;
    private String headerTransfor;

    @Min(value = 0, message = "OpenAuth 最小不能小于0")
    @Max(value = 1, message = "OpenAuth 最大不能超过1")
    private int openAuth;

    private String blackList;
    private String whiteList;
    private int clientipFlowLimit;

    @Min(value = 0, message = "ServiceFlowLimit 最小不能小于0")
    private int serviceFlowLimit;

    @Min(value = 0, message = "RoundType 最小不能小于0")
    @Max(value = 3, message = "RoundType 最大不能超过3")
    private int roundType;
    private String ipList;
    private String weightList;

    @Min(value = 0, message = "UpstreamConnectTimeout 最小不能小于0")
    private int upstreamConnectTimeout;

    @Min(value = 0, message = "UpstreamHeaderTimeout 最小不能小于0")
    private int upstreamHeaderTimeout;

    @Min(value = 0, message = "UpstreamIdleTimeout 最小不能小于0")
    private int upstreamIdleTimeout;

    @Min(value = 0, message = "UpstreamMaxIdle 最小不能小于0")
    private int upstreamMaxIdle;
}
