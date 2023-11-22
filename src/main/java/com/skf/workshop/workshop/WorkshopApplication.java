package com.skf.workshop.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.skf.workshop.workshop.storage.ImageStorageService;
import com.skf.workshop.workshop.storage.StorageProperties;
import com.skf.workshop.workshop.storage.StorageService;




@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService, ImageStorageService imageStorageService ) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();

			imageStorageService.init();
		};
	}

}
