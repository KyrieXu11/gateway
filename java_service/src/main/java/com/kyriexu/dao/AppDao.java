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
}
