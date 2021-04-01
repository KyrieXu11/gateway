package com.kyriexu;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

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
@EnableTransactionManagement
@MapperScans(value = {
        @MapperScan(basePackages = "com.kyriexu.dao")
})
@EnableAspectJAutoProxy
public class MainApplication {
    /**
     * main method
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.build();
    }
}
