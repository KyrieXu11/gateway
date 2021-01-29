package com.kyriexu.rpc.resp;

import com.kyriexu.component.WhiteListConfig;
import com.kyriexu.service.MatcherService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
     * gRpc Server.
     */
    private Server server;
    /**
     * gRpc Server Port.
     */
    @Value("${grpc.port}")
    private int port;
    /**
     * white list config bean
     */
    @Autowired
    private WhiteListConfig whiteListConfig;

    /**
     * non-arg constructor
     * no need to pass any parameter
     */
    @PostConstruct
    public void init() {
        MatcherService service = new MatcherService(whiteListConfig.getUrls());
        this.server = ServerBuilder
                .forPort(port)
                .addService(service)
                .build();
        LOGGER.info("initialized gRpc Server");
    }

    /**
     * start gRpc Server
     *
     * @throws IOException ioExcept
     */
    public void start() throws IOException {
        server.start();
        LOGGER.info("gRpc Server run on port:{}", port);
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

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
            LOGGER.info("准备停止gRpc服务器");
        }
    }
}
