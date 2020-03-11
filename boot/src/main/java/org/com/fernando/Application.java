package org.com.fernando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.com.fernando"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(org.com.fernando.Application.class, args);
	}

}
