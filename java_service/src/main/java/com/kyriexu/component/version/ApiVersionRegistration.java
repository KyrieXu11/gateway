package com.kyriexu.component.version;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author KyrieXu
 * @since 2021/3/24 17:25
 **/
@Configuration
public class ApiVersionRegistration implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CusHandlerMapping();
    }
}
