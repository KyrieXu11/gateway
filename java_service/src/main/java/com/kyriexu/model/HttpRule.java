package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
