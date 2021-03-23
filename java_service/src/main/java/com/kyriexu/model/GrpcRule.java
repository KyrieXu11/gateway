package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrpcRule {
    private Long id;
    private Long serviceId;
    private Integer port;
    private String headerTransfor;
}
