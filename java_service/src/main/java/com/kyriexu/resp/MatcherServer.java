package com.kyriexu.resp;

import com.kyriexu.rpc.AntPathMatcherGrpc;
import com.kyriexu.rpc.Paths;
import com.kyriexu.rpc.Result;
import com.kyriexu.utils.AntPathMatcher;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author KyrieXu
 * @since 2021/1/21 0:17
 **/
public class MatcherServer {
    public static final Logger logger = LoggerFactory.getLogger(MatcherServer.class);

    private final int port;
    private final Server server;


    public MatcherServer(int port) {
        this.port = port;
        this.server = ServerBuilder
                .forPort(port)
                .addService(new MatcherService())
                .build();
    }

    public static void main(String[] args) throws Exception {
        MatcherServer server = new MatcherServer(9000);
        server.start();
        server.blockUntilShutdown();
    }

    public void start() throws IOException {
        server.start();
        logger.info("Server run on port:{}", port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            logger.error("*** shutting down gRPC server since JVM is shutting down");
            try {
                MatcherServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            logger.error("*** server shut down");
        }));
    }

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

    private static class MatcherService extends AntPathMatcherGrpc.AntPathMatcherImplBase {

        @Override
        public StreamObserver<Paths> match(StreamObserver<Result> responseObserver) {
            return new StreamObserver<Paths>() {
                boolean res = false;

                @Override
                public void onNext(Paths point) {
                    String pattern = point.getPattern();
                    String realPath = point.getRealPath();
                    AntPathMatcher matcher = new AntPathMatcher();
                    this.res = matcher.match(pattern, realPath);
                }

                @Override
                public void onError(Throwable throwable) {
                    logger.error("出错了");
                }

                @Override
                public void onCompleted() {
                    responseObserver.onNext(Result.newBuilder().setRes(res).build());
                    responseObserver.onCompleted();
                    logger.info("成功调用，结果是：{}", res);
                }
            };
        }
    }
}
