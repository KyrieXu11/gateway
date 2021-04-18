package com.kyriexu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TcpRule {
    private Long id;
    private Long serviceId;
    private Integer port;
}
