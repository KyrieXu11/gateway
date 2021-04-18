package com.kyriexu.common.utils;

import com.kyriexu.dto.AppListItem;
import com.kyriexu.model.App;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/14 15:52
 **/
public class HttpUtilsTest {

    @Test
    public void get() throws ParseException {
        // 2006-01-02T15:04:05Z07:00
        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .format(new Date()));
    }

    @Test
    public void post() {
        List<App> appList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            App app = new App();
            app.setAppId("123");
            app.setId(1L);
            app.setDeleted(false);
            app.setQpd(123);
            app.setQps(123);
            appList.add(app);
            app.setSecret("234234");
        }
        class TempDto {
            public List<App> list;
        }
        TempDto tempDto = new TempDto();
        tempDto.list = appList;
        try {
            List<AppListItem> post = HttpUtils.post("http://127.0.0.1:8081/app/list", tempDto,List.class);
            System.out.println(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}