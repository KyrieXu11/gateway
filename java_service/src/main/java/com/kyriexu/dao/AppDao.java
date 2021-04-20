package com.kyriexu.dao;

import com.kyriexu.model.App;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/26 15:00
 **/
public interface AppDao {
    List<App> getAppList(@Param("page") int page, @Param("size") int size, @Param("query") String query);

    Integer count(@Param("query") String info);

    int update(@Param("app") App app);

    App get(@Param("id") Long id);

    App getByAppId(@Param("appId") String appId);

    int add(@Param("app") App app);
}
