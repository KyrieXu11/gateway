package com.kyriexu.dao;

import com.kyriexu.model.AccessControl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KyrieXu
 * @since 2021/4/11 14:24
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccessControlDaoTest {
    @Autowired
    private AccessControlDao accessControlDao;

    @Test
    public void updateAccessControl() {
        AccessControl accessControl = new AccessControl();
        accessControl.setServiceId(10000L);
        accessControl.setBlackList("123.123.123,123");
        int i = accessControlDao.update(accessControl);
        Assert.assertEquals(0,i);
    }
}