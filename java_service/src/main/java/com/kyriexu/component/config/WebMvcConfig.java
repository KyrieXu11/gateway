package com.kyriexu.component.config;

import com.kyriexu.component.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author KyrieXu
 * @since 2021/1/31 21:31
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器的执行顺序根据 order 递增执行
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .order(1);
    }
}
