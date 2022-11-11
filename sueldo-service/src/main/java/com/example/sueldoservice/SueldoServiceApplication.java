package com.example.sueldoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SueldoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SueldoServiceApplication.class, args);
	}

}
