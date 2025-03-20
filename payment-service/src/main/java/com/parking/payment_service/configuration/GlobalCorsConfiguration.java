package com.parking.payment_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*") //frontend domain
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
