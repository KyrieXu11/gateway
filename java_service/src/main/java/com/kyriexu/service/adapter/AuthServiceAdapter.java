package com.kyriexu.service.adapter;

import com.kyriexu.rpc.matchrpc.GoRequest;
import com.kyriexu.service.AuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/1/30 13:39
 **/
public abstract class AuthServiceAdapter implements AuthService {

    @Override
    public boolean checkAuth(GoRequest req) {
        return true;
    }

    @Override
    public boolean checkAuth(HttpServletRequest request) {
        return true;
    }
}
