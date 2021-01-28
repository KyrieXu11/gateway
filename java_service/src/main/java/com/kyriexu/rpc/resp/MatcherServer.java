package com.kyriexu.rpc.resp;

import com.kyriexu.rpc.matchrpc.AntPathMatcherGrpc;
import com.kyriexu.rpc.matchrpc.Paths;
import com.kyriexu.rpc.matchrpc.Result;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author KyrieXu
 * @since 2021/1/21 0:17
 **/
@Component
public class MatcherServer {
    /**
     * logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MatcherServer.class);

    /**
     * gRpc Server
     */
    private final Server server;

    @Value("${grpc.port}")
    private int port;

    /**
     * non-arg constructor
     * no need to pass any parameter
     */
    public MatcherServer() {
        this.server = ServerBuilder
                .forPort(port)
                .addService(new MatcherService())
                .build();
    }

    /**
     * start gRpc Server
     *
     * @throws IOException ioExcept
     */
    public void start() throws IOException {
        server.start();
        LOGGER.info("Server run on port:{}", port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            LOGGER.info("*** shutting down gRPC server since JVM is shutting down");
            try {
                MatcherServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            LOGGER.info("*** server shut down");
        }));
    }

    /**
     * stop gRpc Server
     *
     * @throws InterruptedException n
     */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Matcher Service
     * <p>
     * this class provide real match() method
     */
    private static class MatcherService extends AntPathMatcherGrpc.AntPathMatcherImplBase {

        @Override
        public StreamObserver<Paths> match(StreamObserver<Result> responseObserver) {
            return new StreamObserver<Paths>() {
                /**
                 * response result
                 */
                boolean res = false;

                @Override
                public void onNext(Paths point) {
                    String pattern = point.getPattern();
                    String realPath = point.getRealPath();
                    PathMatcher matcher = new AntPathMatcher();
                    this.res = matcher.match(pattern, realPath);
                    LOGGER.info("pattern是 {} 请求路径是 {} 结果是：{}", pattern, realPath, res);
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.error("出错了");
                }

                @Override
                public void onCompleted() {
                    responseObserver.onNext(Result.newBuilder().setRes(res).build());
                    responseObserver.onCompleted();
                    LOGGER.info("成功调用");
                }
            };
        }
    }
}
