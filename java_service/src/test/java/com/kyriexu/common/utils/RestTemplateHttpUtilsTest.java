package com.kyriexu.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.dto.AppListItem;
import com.kyriexu.model.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/4/14 17:27
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateHttpUtilsTest {

    @Autowired
    private RestTemplateHttpUtils restTemplateHttpUtils;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testPostForObject() {
        List<App> appList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            App app = new App();
            app.setAppId("123");
            app.setId(1L);
            app.setCreateAt(new Date());
            app.setUpdateAt(new Date());
            app.setDeleted(false);
            app.setQpd(123);
            app.setQps(123);
            appList.add(app);
            app.setSecret("234234");
        }
        try {
            String s = mapper.writeValueAsString(appList);
            Map<String, String> a = new HashMap<>();
            a.put("a", s);
            List<AppListItem> list = restTemplateHttpUtils.postForObject("http://127.0.0.1:8081/app/list", a, List.class);
            System.out.println(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getInternalGenericObject() {
    }

    @Test
    public void postForObject() {
    }
}