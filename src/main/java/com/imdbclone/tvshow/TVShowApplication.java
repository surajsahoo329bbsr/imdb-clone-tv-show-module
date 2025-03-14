package com.imdbclone.tvshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TVShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TVShowApplication.class, args);
	}
}