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
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public App detail(Long id) {
        return appDao.get(id);
    }

    @Override
    public List<App> getAppList(SearchInput input) {
        int page = Utils.getPage(input.getPage(), input.getSize());
        return appDao.getAppList(page, input.getSize(), input.getInfo());
    }

    @Override
    public boolean add(App app) {
        String appId = app.getAppId();

        App ans = appDao.getByAppId(appId);
        if (ans != null) {
            throw new BaseException(ResultCode.APP_ALREADY_EXIST);
        }
        if (StringUtils.isEmpty(app.getSecret())) {
            app.setSecret(UUID.randomUUID().toString().substring(0, 3));
        }
        try {
            String sec = Utils.encode("md5", app.getSecret());
            app.setSecret(sec);
            app.setCreateAt(new Date());
            app.setUpdateAt(new Date());
            app.setDeleted(false);
            int rows = appDao.add(app);
            return rows > 0;
        } catch (NoSuchAlgorithmException e) {
            logger.error("encode secret failed,cause", e);
            throw new BaseException(ResultCode.INTERNAL_EXCEPTION);
        }
    }

    @SneakyThrows
    @Override
    public boolean update(App app) {
        Long id = app.getId();
        if (null == id) {
            throw new BaseException(ResultCode.APP_INFORMATION_ERROR);
        }
        String secret = app.getSecret();
        if (!StringUtils.isEmpty(secret)) {
            secret = Utils.encode("md5", secret.substring(0, 3));
            app.setSecret(secret);
        }
        app.setUpdateAt(new Date());
        int row = appDao.update(app);
        return row > 0;
    }

    @Override
    public boolean del(Long id) {
        App app = appDao.get(id);
        if (app != null && app.isDeleted()) {
            throw new BaseException(ResultCode.APP_ALREADY_DELETED);
        } else if (app == null) {
            throw new BaseException(ResultCode.APP_NO_EXIST);
        }
        app = new App();
        app.setId(id);
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
            List<AppListItem> res = HttpUtils.post(url, appListDto, List.class);
            PageBean<AppListItem> pageBean = new PageBean<>();
            pageBean.setItems(res);
            pageBean.setCurrent(input.getPage());
            pageBean.setTotal(getTotalPage(input));
            return pageBean;
        } catch (IOException e) {
            logger.error("[FAIL] invoke getAppPageBean() cause:", e.getCause());
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
