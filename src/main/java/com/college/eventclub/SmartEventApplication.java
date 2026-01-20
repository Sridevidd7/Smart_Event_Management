package com.college.eventclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Smart Event Management System - Spring Boot Application
 * Web-based event management system accessible via HTTP
 */
@SpringBootApplication
public class SmartEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartEventApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Smart Event Management System Running");
        System.out.println("Access at: http://localhost:8080");
        System.out.println("========================================\n");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
