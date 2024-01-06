package com.luckraw.agregadorinvestimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgregadorInvestimentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadorInvestimentosApplication.class, args);
	}

}
