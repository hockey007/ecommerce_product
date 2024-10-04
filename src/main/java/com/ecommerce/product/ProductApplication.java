package com.ecommerce.product;

import config.GrpcClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce.product")
@Import(GrpcClientConfig.class)
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
