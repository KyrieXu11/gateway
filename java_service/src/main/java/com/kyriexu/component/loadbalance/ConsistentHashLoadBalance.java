package com.kyriexu.component.loadbalance;

import java.util.*;

/**
 * @author KyrieXu
 * @since 2021/3/14 21:10
 **/
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    private final List<Integer> keys;
    private final Map<Integer, String> map;
    private final Integer replicas;

    public ConsistentHashLoadBalance(Integer replicas) {
        this.keys = new ArrayList<>();
        this.map = new HashMap<>();
        this.replicas = replicas;
    }

    @Override
    public void addHosts(String... hosts) {
        String host = hosts[0];
        for (int i = 0; i < replicas; i++) {
            Integer hash = hash(i + host);
            this.keys.add(hash);
            this.map.put(hash, host);
        }
        Collections.sort(keys);
    }

    @Override
    public String get(String key) {
        Integer hash = hash(key);
        int idx = binarySearch(keys.toArray(new Integer[0]), hash);
        if (idx == keys.size()) {
            idx = 0;
        }
        return this.map.get(keys.get(idx));
    }

    private int binarySearch(Integer[] keys, Integer hash) {
        int left = 0, right = keys.length;
        while (left < right) {
            int mid = left + right >> 1;
            if (keys[mid] >= hash) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private Integer hash(String key) {
        return Objects.hash(key);
    }
}
