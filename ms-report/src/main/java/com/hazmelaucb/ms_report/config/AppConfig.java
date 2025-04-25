package com.hazmelaucb.ms_report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazmelaucb.ms_report.service.MsReportService;
import com.hazmelaucb.ms_report.repository.ReportRepository;


@Configuration
public class AppConfig {

    @Bean
    public MsReportService msReportService(ReportRepository reportRepository) {
        return new MsReportService(reportRepository);
    }
}
