package com.kyriexu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:20
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetail {
    private ServiceInfo info;
    private HttpRule httpRule;
    private TcpRule tcpRule;
    private GrpcRule grpcRule;
    private LoadBalance loadBalance;
}
