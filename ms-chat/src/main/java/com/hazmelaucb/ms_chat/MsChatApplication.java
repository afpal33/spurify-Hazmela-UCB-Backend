package com.hazmelaucb.ms_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // <— IMPORT NECESARIO

@SpringBootApplication
@EnableDiscoveryClient
public class MsChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsChatApplication.class, args);
	}

}
