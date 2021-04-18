package com.kyriexu.dao;

import com.kyriexu.model.App;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KyrieXu
 * @since 2021/4/15 11:13
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppDaoTest {

    @Autowired
    private AppDao appDao;

    @Test
    public void get() {
        App app = appDao.get(123L);
        Assert.assertNotNull(app);
    }
}