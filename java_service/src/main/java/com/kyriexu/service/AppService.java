package com.kyriexu.service;

import com.kyriexu.dto.AppListItem;
import com.kyriexu.dto.SearchInput;
import com.kyriexu.model.App;
import com.kyriexu.model.PageBean;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/14 13:02
 **/
public interface AppService {

    PageBean<AppListItem> getAppPageBean(SearchInput input);

    boolean del(Long id);

    App detail(Long id);

    List<App> getAppList(SearchInput input);

    boolean add(App app);

    boolean update(App app);
}
