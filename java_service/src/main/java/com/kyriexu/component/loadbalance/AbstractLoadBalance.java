package com.kyriexu.component.loadbalance;

import com.kyriexu.component.LoadBalance;

/**
 * @author KyrieXu
 * @since 2021/3/14 11:19
 **/
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public final void add(String... param) {
        if (checkHost(param)) {
            addHosts(param);
        } else {
            throw new RuntimeException("the param passed is not correct");
        }
    }

    @Override
    public String get(String key) {
        return "";
    }

    @Override
    public String get() {
        return "";
    }

    private boolean checkHost(String... hosts) {
        int length = hosts.length;
        switch (length) {
            case 1:
                return checkHost(hosts[0]);
            case 2:
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean checkHost(String host) {
        return true;
    }

    public abstract void addHosts(String... hosts);
}
