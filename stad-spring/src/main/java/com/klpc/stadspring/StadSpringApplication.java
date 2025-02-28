package com.klpc.stadspring;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing // 배치 기능 활성화
@EnableScheduling
public class StadSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(StadSpringApplication.class, args);
	}

}
