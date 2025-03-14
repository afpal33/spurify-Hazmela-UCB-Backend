package com.hazmelaucb.ms_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.hazmelaucb.ms_report.config")
@ComponentScan(basePackages = {
    "com.hazmelaucb.ms_report.config",
    "com.hazmelaucb.ms_report.controller",
    "com.hazmelaucb.ms_report.config",
    "com.hazmelaucb.ms_report.config",
    "com.hazmelaucb.ms_report.config",
    "com.hazmelaucb.ms_report.config"  
})

@SpringBootApplication
public class MsReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReportApplication.class, args);
	}

}
