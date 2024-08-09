package com.dev.kioki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KiokiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiokiApplication.class, args);
	}

}
