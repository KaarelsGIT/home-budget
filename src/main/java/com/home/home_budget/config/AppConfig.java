package com.home.home_budget.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(
                    "http://localhost:4200",
                    "http://192.168.1.210:4200",
                    "http://192.168.1.156:4200",
                    "http://172.20.10.14:4200"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*");
    }

}
