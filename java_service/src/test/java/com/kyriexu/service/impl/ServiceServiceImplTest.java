package com.kyriexu.service.impl;

import com.kyriexu.service.ServiceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KyrieXu
 * @since 2021/4/11 14:57
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceServiceImplTest {
    @Autowired
    private ServiceService serviceService;

    @Test
    public void del() {
        boolean del = serviceService.del(34L);
        Assert.assertFalse(del);
    }
}