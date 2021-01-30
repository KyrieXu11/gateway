package com.kyriexu.service;


import com.kyriexu.rpc.matchrpc.AntPathMatcherGrpc;
import com.kyriexu.rpc.matchrpc.GoRequest;
import com.kyriexu.rpc.matchrpc.Result;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/1/30 1:46
 **/
public class MatcherService extends AntPathMatcherGrpc.AntPathMatcherImplBase {
    /**
     * logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MatcherService.class);

    /**
     * white list map.
     * because this class is not marked as component
     * so we can get autowired attribute in the way of setting this attribute
     */
    private Map<String, List<String>> whiteList;

    /**
     * auth service
     */
    private AuthService authService;

    /**
     * one param constructor,set authservice
     *
     * @param authService authservice
     */
    public MatcherService(AuthService authService) {
        this.authService = authService;
        LOGGER.info("initialized matcher service");
    }

    /**
     * one param constructor,set whitelist
     *
     * @param whiteList white list map
     */
    @Deprecated
    public MatcherService(Map<String, List<String>> whiteList) {
        this.whiteList = whiteList;
        LOGGER.info("initialized matcher service");
    }

    /**
     * authservice getter
     *
     * @return authservice
     */
    public AuthService getAuthService() {
        return authService;
    }

    /**
     * authservice setter
     *
     * @param authService authservice
     */
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * @return white list map
     */
    public Map<String, List<String>> getWhiteList() {
        return whiteList;
    }

    /**
     * set method
     *
     * @param whiteList white list
     */
    public void setWhiteList(Map<String, List<String>> whiteList) {
        this.whiteList = whiteList;
    }

    /**
     * real match() method implementation
     *
     * @param responseObserver gRpc response object
     * @return gRpc param
     */
    @Override
    public StreamObserver<GoRequest> match(StreamObserver<Result> responseObserver) {
        return new StreamObserver<GoRequest>() {

            /**
             * match result
             */
            boolean res = false;

            @Override
            public void onNext(GoRequest req) {
                res = authService.checkAuth(req);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.error("err cause {}", t.toString());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Result.newBuilder().setRes(res).build());
                responseObserver.onCompleted();
                LOGGER.info("match method completed");
            }
        };
    }

}
