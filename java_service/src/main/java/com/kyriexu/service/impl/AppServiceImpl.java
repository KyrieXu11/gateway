package com.kyriexu.service.impl;

import com.kyriexu.common.utils.HttpUtils;
import com.kyriexu.common.utils.Utils;
import com.kyriexu.dao.AppDao;
import com.kyriexu.dto.AppListItem;
import com.kyriexu.dto.ListDto;
import com.kyriexu.dto.SearchInput;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.model.App;
import com.kyriexu.model.PageBean;
import com.kyriexu.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/4/14 15:09
 **/
@Service
public class AppServiceImpl implements AppService {

    public static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private HttpServletRequest request;

    @Value("${go-service.host}")
    private String addr;

    @Autowired
    private AppDao appDao;

    @Override
    public App detail(String appId) {
        return appDao.get(appId);
    }

    @Override
    public List<App> getAppList(SearchInput input) {
        int page = Utils.getPage(input.getPage(), input.getSize());
        return appDao.getAppList(page, input.getSize(), input.getInfo());
    }

    @Override
    public boolean del(String appId) {
        App app = appDao.get(appId);
        if (app != null && app.isDeleted()) {
            throw new BaseException(ResultCode.APP_ALREADY_DELETED);
        } else if (app == null) {
            throw new BaseException(ResultCode.APP_NO_EXIST);
        }
        app = new App();
        app.setAppId(appId);
        app.setDeleted(true);
        app.setUpdateAt(new Date());
        int rows = appDao.update(app);
        return rows > 0;
    }

    @Override
    public PageBean<AppListItem> getAppPageBean(SearchInput input) {
        List<App> appList = this.getAppList(input);
        String url = String.format("http://%s%s", addr, request.getRequestURI());
        logger.info("[INTERNAL CALL] url : {}", url);
        ListDto<App> appListDto = new ListDto<>(appList);
        try {
            List<AppListItem> res = HttpUtils.post(url, appListDto);
            PageBean<AppListItem> pageBean = new PageBean<>();
            pageBean.setItems(res);
            pageBean.setCurrent(input.getPage());
            pageBean.setTotal(getTotalPage(input));
            logger.info("AppListPageBean : {}", pageBean);
            return pageBean;
        } catch (IOException e) {
            logger.error("[FAIL] invoke getAppPageBean()", e.getCause());
            throw new BaseException(ResultCode.INTERNAL_EXCEPTION);
        }
    }

    private int getTotalPage(SearchInput input) {
        int size = input.getSize();
        int res = appDao.count(input.getInfo());
        if (res % size != 0) {
            return res / size + 1;
        }
        return res / size;
    }
}
