package com.hazmelaucb.ms_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.hazmelaucb.ms_report.config")
@ComponentScan(basePackages = {
    "com.hazmelaucb.ms_report.config",
    "com.hazmelaucb.ms_report.controller",
    "com.hazmelaucb.ms_report.dto",
    "com.hazmelaucb.ms_report.exception",
    "com.hazmelaucb.ms_report.service",
    "com.hazmelaucb.ms_report.MsReportService"  
})
public class MsReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReportApplication.class, args);
	}

}
