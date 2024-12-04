package com.AgendaEscolar.AgendaEscolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;



@SpringBootApplication
@EnableAsync
public class AgendaEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaEscolarApplication.class, args);
	}
}
