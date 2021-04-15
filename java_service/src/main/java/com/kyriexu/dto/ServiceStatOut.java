package com.kyriexu.dto;

import lombok.Data;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/10 22:15
 **/
@Data
public class ServiceStatOut {
    private List<Long> yesterday;
    private List<Long> today;
}
