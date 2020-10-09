package com.softwarefoundation.fooddeliverycontroleacesso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FoodDeliveryControleAcessoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryControleAcessoApplication.class, args);
	}

}
