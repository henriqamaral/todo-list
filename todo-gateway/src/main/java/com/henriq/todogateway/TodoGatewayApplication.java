package com.henriq.todogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TodoGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoGatewayApplication.class, args);
	}

}
