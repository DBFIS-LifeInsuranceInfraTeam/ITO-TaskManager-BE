package com.ITOPW.itopw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItopwApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItopwApplication.class, args);
	}

}
