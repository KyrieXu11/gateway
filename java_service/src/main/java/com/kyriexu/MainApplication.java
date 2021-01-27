package com.kyriexu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KyrieXu
 * @since 2021/1/27 22:42
 **/
@SpringBootApplication(scanBasePackages = {"com.kyriexu"})
public class MainApplication {
    /**
     * main method
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
