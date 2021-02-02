package com.kyriexu.service;

import com.kyriexu.rpc.matchrpc.GoRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/1/30 13:22
 **/
public interface AuthService {
    /**
     * check gRpc request
     *
     * @param req gRpc Request
     * @return whether this request can pass
     */
    boolean checkAuth(GoRequest req);

    /**
     * check out normal http request
     *
     * @param request servlet request
     * @return whether this request can pass
     */
    boolean checkAuth(HttpServletRequest request);
}
