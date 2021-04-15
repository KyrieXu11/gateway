package com.kyriexu.dto;

import lombok.Data;

/**
 * @author KyrieXu
 * @since 2021/4/10 15:32
 **/
@Data
public class ServiceStatItemOutput {
    private String name;
    private int loadType;
    private long value;
}
