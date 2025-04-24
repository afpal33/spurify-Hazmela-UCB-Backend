package com.hazmelaucb.ms_report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazmelaucb.ms_report.service.MsReportService;

@Configuration
public   class AppConfig {

    @Bean
    public MsReportService msReportService() {
        return new MsReportService();
    }
}
