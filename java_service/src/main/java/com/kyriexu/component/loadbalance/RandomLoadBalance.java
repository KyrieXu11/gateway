package com.kyriexu.component.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author KyrieXu
 * @since 2021/3/14 11:01
 **/
public class RandomLoadBalance extends AbstractLoadBalance {
    private final List<String> hosts;
    private Integer cur;

    public RandomLoadBalance() {
        // Integer 默认为 null
        cur = 0;
        hosts = new ArrayList<>();
    }

    @Override
    public void addHosts(String... hosts) {
        String host = hosts[0];
        this.hosts.add(host);
    }

    @Override
    public String get() {
        if (this.hosts.size() == 0)
            return "";
        this.cur = new Random().nextInt(hosts.size());
        return hosts.get(cur);
    }
}
