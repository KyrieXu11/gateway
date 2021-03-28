package com.kyriexu.common.handler;

import com.kyriexu.common.utils.Constant;
import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author KyrieXu
 * @since 2021/3/27 17:43
 **/
@Data
public class RedisFlowCounter {
    private final Timer timer;
    private String appId;
    private long interval;
    private long qps;
    private long timeStamp;
    private volatile AtomicLong tickerCount;
    private volatile AtomicLong totalCount;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private RedisTemplate redisTemplate;

    public RedisFlowCounter(String appId, long interval, ThreadPoolTaskExecutor threadPoolTaskExecutor, RedisTemplate redisTemplate) {
        this.appId = appId;
        this.interval = interval;
        this.timer = new Timer();
        this.tickerCount = new AtomicLong(0);
        this.totalCount = new AtomicLong(0);
        this.redisTemplate = redisTemplate;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.threadPoolTaskExecutor.execute(this::startTask);
    }

    private void startTask() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                String dayKey = getDayKey(now);
                String hourKey = getHourKey(now);
                redisTemplate.executePipelined(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection c) throws DataAccessException {
                        byte[] dayKeyBytes = dayKey.getBytes();
                        byte[] hourKeyBytes = hourKey.getBytes();
                        c.incrBy(dayKeyBytes, tickerCount.get());
                        c.expire(dayKeyBytes, Constant.REDIS_DAY_KEY_EXPIRE);
                        c.incrBy(hourKeyBytes, tickerCount.get());
                        c.expire(hourKeyBytes, Constant.REDIS_HOUR_KEY_EXPIRE);
                        return null;
                    }
                });

            }
        }, 0, this.interval);
        // 注册到JVM钩子函数当中取消 timer 的运行
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDownTimer));
    }

    private void shutDownTimer() {
        this.timer.cancel();
    }

    private String getDayKey(LocalDateTime time) {
        String dayKey = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("%s_%s_%s", Constant.REDIS_FLOW_DAY_KEY, dayKey, appId);
    }

    private String getHourKey(LocalDateTime time) {
        String hourKey = time.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        return String.format("%s_%s_%s", Constant.REDIS_FLOW_HOUR_KEY, hourKey, appId);
    }

    private long getData(LocalDateTime time) {
        return (long) this.redisTemplate.opsForValue().get(getDayKey(time));
    }
}
