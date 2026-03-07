package com.mvr.plant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MvrPlantProjectApplication {
	public static void main(String[] args) {SpringApplication.run(MvrPlantProjectApplication.class, args);
	}
}