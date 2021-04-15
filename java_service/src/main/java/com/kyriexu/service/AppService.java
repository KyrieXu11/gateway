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

    boolean del(String appId);

    App detail(String appId);

    List<App> getAppList(SearchInput input);
}
