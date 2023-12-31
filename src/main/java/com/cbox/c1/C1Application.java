package com.cbox.c1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class C1Application {

	public static void main(String[] args) {
		SpringApplication.run(C1Application.class, args);
	}

}
