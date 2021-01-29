package com.kyriexu.service;

import com.kyriexu.component.WhiteListConfig;
import com.kyriexu.rpc.GoRequest;
import com.kyriexu.rpc.Result;
import com.kyriexu.rpc.matchrpc.AntPathMatcherGrpc;
import com.kyriexu.rpc.resp.MatcherServer;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KyrieXu
 * @since 2021/1/30 1:46
 **/
public class MatcherService extends AntPathMatcherGrpc.AntPathMatcherImplBase {
    /**
     * logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MatcherService.class);

    private Map<String, List<String>> whiteList;

    public MatcherService(Map<String, List<String>> whiteList) {
        this.whiteList = whiteList;
        LOGGER.info("initialized matcher service");
    }

    public Map<String, List<String>> getWhiteList() {
        return whiteList;
    }

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
                PathMatcher matcher = new AntPathMatcher();

                String method = req.getMethod().toLowerCase();
                String addr = req.getRemoteAddr();
                String path = req.getRealPath();

                // 由于使用了并发 map 所以为了保证线程安全，使用原子类
                AtomicInteger cnt = new AtomicInteger(0);
                whiteList.forEach((type, urls) -> {
                    if (type.equals(method)) {
                        urls.forEach((url) -> {
                            if (matcher.match(url, addr)) {
                                res = true;
                            }
                        });
                    }
                    if (cnt.get() == 4)
                        return;
                    cnt.getAndIncrement();
                });
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Result.newBuilder().setRes(res).build());
                responseObserver.onCompleted();
            }
        };
    }
}
