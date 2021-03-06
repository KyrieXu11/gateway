package com.kyriexu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KyrieXu
 * @since 2021/3/24 12:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSearch {
    private Long serviceId;
    private String serviceType;
}
