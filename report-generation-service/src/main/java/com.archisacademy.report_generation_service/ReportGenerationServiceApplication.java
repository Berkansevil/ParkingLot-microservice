package com.archisacademy.report_generation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReportGenerationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportGenerationServiceApplication.class, args);
    }
}