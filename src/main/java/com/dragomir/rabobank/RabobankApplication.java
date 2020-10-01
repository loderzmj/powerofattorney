package com.dragomir.rabobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class RabobankApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabobankApplication.class, args);
	}

}
