package com.kyriexu.common.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author KyrieXu
 * @since 2021/3/28 13:26
 **/
@Component
public class FlowCounterHandler {
    private final ReadWriteLock lock;
    private final Map<String, RedisFlowCounter> redisCounterMap;
    private final List<RedisFlowCounter> redisCounterList;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private RedisTemplate redisTemplate;

    public FlowCounterHandler() {
        this.lock = new ReentrantReadWriteLock();
        this.redisCounterList = new ArrayList<>();
        this.redisCounterMap = new HashMap<>();
    }

    public RedisFlowCounter getCounter(String serviceName) {
        for (RedisFlowCounter redisFlowCounter : this.redisCounterList) {
            if (serviceName.equals(redisFlowCounter.getAppId())) {
                return redisFlowCounter;
            }
        }
        RedisFlowCounter redisFlowCounter = new RedisFlowCounter(serviceName, TimeUnit.SECONDS.toMillis(10), this.executor, this.redisTemplate);
        try {
            this.lock.writeLock().lock();
            this.redisCounterList.add(redisFlowCounter);
            this.redisCounterMap.put(serviceName, redisFlowCounter);
            return redisFlowCounter;
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
