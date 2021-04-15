package com.kyriexu.dto;

import lombok.Data;

/**
 * @author KyrieXu
 * @since 2021/4/10 15:43
 **/
@Data
public class InternResp<T> {
    private int code;
    private String msg;
    private T data;
    private String traceId;
    private Object stack;
}
