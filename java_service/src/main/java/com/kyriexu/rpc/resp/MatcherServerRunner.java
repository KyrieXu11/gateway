package com.kyriexu.rpc.resp;

import com.kyriexu.component.WhiteListConfig;
import com.kyriexu.service.MatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author KyrieXu
 * @since 2021/1/27 22:54
 **/
@Component
public class MatcherServerRunner implements ApplicationRunner {

    @Autowired
    private WhiteListConfig whiteListConfig;

    /**
     * gRpc 服务器
     */
    private MatcherServer server;

    /**
     * MatcherServer getter
     *
     * @return matcherServer
     */
    public MatcherServer getServer() {
        return server;
    }

    /**
     * MatcherServer setter
     *
     * @param server matchServer
     */
    @Autowired
    public void setServer(MatcherServer server) {
        this.server = server;
    }

    /**
     * this method will be called automatically after spring container is initialized
     * so i can run rpc server in this method
     * besides this method you can implements CommandLineRunner to get the same effect
     *
     * @param args args of main method see {@link com.kyriexu.MainApplication}
     * @throws Exception RuntimeException
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 主线程执行就好了
        server.start();
        server.blockUntilShutdown();
    }
}
