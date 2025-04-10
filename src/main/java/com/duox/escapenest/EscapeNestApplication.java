package com.duox.escapenest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.duox.escapenest.repository.client")
public class EscapeNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscapeNestApplication.class, args);
	}

}
