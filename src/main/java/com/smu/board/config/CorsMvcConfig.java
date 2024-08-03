package com.smu.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        
        corsRegistry.addMapping("/**") // 허용할 API 호출 주소 패턴
                .allowedOrigins("*") // 허용할 CALLER
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // 허용할 METHODS
    }
}