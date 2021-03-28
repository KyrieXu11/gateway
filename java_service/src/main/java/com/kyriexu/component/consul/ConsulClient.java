package com.kyriexu.component.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.health.ServiceHealth;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/27 11:21
 **/
public class ConsulClient {
    private final Consul client;

    public ConsulClient() {
        client = Consul.builder().build();
    }

    /**
     * @param addr consul 的地址 xxx.xxx.xxx.xxx:xxxxx
     */
    public ConsulClient(String addr) {
        client = Consul.builder().withHostAndPort(HostAndPort.fromHost(addr)).build();
    }

    public void registerService(ConsulService service) throws NotRegisteredException {
        AgentClient client = this.client.agentClient();
        Registration s = ImmutableRegistration.builder()
                .address(service.getAddress())
                .id(service.getServiceId())
                .name(service.getName())
                .port(service.getPort())
                .check(Registration.RegCheck.ttl(service.getCheckInterval()))
                .tags(service.getTags())
                .meta(service.getMeta())
                .build();
        client.register(s);
        client.pass(service.getServiceId());
    }

    public List<ServiceHealth> findHealthyService(String serviceName) {
        HealthClient healthClient = client.healthClient();
        return healthClient.getHealthyServiceInstances(serviceName).getResponse();
    }
}
