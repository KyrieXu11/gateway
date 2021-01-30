package com.kyriexu.service;

import com.kyriexu.rpc.matchrpc.GoRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/1/30 13:22
 **/
public interface AuthService {
    /**
     * @param req gRpc Request
     * @return whether this request can pass
     */
    boolean checkAuth(GoRequest req);

    boolean checkAuth(HttpServletRequest request);
}
