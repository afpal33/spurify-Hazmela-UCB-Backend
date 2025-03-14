package com.hazmelaucb.ms_rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hazmelaucb.ms_rating.feign")  // Adjust package if needed
public class MsRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRatingApplication.class, args);
	}

}
