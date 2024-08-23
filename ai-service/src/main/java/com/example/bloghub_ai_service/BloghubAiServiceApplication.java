package com.example.bloghub_ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BloghubAiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloghubAiServiceApplication.class, args);
	}

}
