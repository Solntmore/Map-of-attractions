package ru.digitalchief.Map.of.attractions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MapOfAttractionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapOfAttractionsApplication.class, args);
	}

}
