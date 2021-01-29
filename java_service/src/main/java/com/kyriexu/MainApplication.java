package com.kyriexu;

import com.kyriexu.service.MatcherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author KyrieXu
 * @since 2021/1/27 22:42
 **/
@SpringBootApplication(scanBasePackages = {"com.kyriexu"})
@EnableConfigurationProperties
@PropertySources({
        @PropertySource(value = {
                "classpath:whitelist.properties"
        }, encoding = "UTF-8")
})
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
