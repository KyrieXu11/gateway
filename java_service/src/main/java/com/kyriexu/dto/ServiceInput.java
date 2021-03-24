package com.kyriexu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KyrieXu
 * @since 2021/3/24 11:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInput {
    private int page;
    private int size;
    private String info;
}
