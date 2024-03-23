package com.ingleside.popeh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class PopehApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopehApplication.class, args);
	}

}
