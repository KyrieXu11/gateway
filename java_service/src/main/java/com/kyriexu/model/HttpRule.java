package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpRule {
    private Long id;
    private Long serviceId;
    private int ruleType;
    private String rule;
    private int needHttps;
    private int needStripUri;
    private int needWebSocket;
    private String urlRewrite;
    private String headerTransfor;

    public HttpRule(Long serviceId, int ruleType, String rule, int needHttps, int needStripUri, int needWebSocket, String urlRewrite, String headerTransfor) {
        this.serviceId = serviceId;
        this.ruleType = ruleType;
        this.rule = rule;
        this.needHttps = needHttps;
        this.needStripUri = needStripUri;
        this.needWebSocket = needWebSocket;
        this.urlRewrite = urlRewrite;
        this.headerTransfor = headerTransfor;
    }
}
