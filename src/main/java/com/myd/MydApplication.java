package com.myd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
//@EnableScheduling
public class MydApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydApplication.class, args);
	}
}
