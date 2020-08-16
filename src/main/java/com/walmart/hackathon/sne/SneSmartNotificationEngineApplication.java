package com.walmart.hackathon.sne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.*;

@SpringBootApplication
@EnableScheduling
public class SneSmartNotificationEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SneSmartNotificationEngineApplication.class, args);
	}

}
