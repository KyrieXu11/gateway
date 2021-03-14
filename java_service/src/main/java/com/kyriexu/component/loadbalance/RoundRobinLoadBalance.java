package com.kyriexu.component.loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/14 11:28
 **/
public class RoundRobinLoadBalance extends AbstractLoadBalance {
    private final List<String> hosts;
    private Integer cur;

    public RoundRobinLoadBalance() {
        cur = 0;
        hosts = new ArrayList<>();
    }

    @Override
    public String get() {
        int size = hosts.size();
        if (cur >= size) {
            cur = 0;
        }
        String curAddr = hosts.get(cur);
        cur = (cur + 1) % size;
        return curAddr;
    }

    @Override
    public void addHosts(String... node) {
        this.hosts.add(node[0]);
    }
}
