package com.kyriexu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/14 17:57
 **/
@Data
@AllArgsConstructor
public class ListDto<T> {
    private List<T> list;
}
