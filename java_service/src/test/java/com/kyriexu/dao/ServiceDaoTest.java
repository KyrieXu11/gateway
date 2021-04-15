package com.kyriexu.dao;

import com.kyriexu.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/4/11 13:39
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceDaoTest {
    @Autowired
    private ServiceDao serviceDao;

    @Test
    public void TestInvalidParamUpdate(){
        ServiceInfo info = new ServiceInfo();
        info.setId(100000L);
        info.setLoadType(1);
        int i = serviceDao.updateServiceInfo(info);
        Assert.assertEquals(0,i);
    }

    @Test
    public void saveServiceInfo() {
        ServiceInfo info = new ServiceInfo(
                1,
                "123",
                "123",
                new Date(),
                new Date(),
                false
        );
        long l = serviceDao.saveServiceInfo(info);
        System.out.println(info.getId());
    }
}
