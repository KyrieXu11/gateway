package com.kyriexu.service.impl;

import com.kyriexu.common.utils.Constant;
import com.kyriexu.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author KyrieXu
 * @since 2021/4/2 17:55
 **/
@Service
public class SessionSubmitServiceImpl implements SubmitService {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void removeToken(String token) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constant.X_GATEWAY_TOKEN);
    }

    @Override
    public void saveToken(String token) {
        HttpSession session = request.getSession();
        session.setAttribute(Constant.X_GATEWAY_TOKEN, token);
    }

    @Override
    public boolean isTokenExist(String token) {
        HttpSession session = request.getSession();
        String t = (String) session.getAttribute(Constant.X_GATEWAY_TOKEN);
        return !StringUtils.isEmpty(t) && t.equals(token);
    }
}
