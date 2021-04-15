package com.kyriexu.dao;

import com.kyriexu.model.LoadBalance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KyrieXu
 * @since 2021/4/11 14:05
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoadBalanceDaoTest {

    @Autowired
    private LoadBalanceDao loadBalanceDao;

    @Test
    public void update() {
        LoadBalance loadBalance = new LoadBalance();
        loadBalance.setId(100000L);
        loadBalance.setServiceId(100000L);
        int rows = loadBalanceDao.update(loadBalance);
        Assert.assertEquals(0,rows);
    }
}