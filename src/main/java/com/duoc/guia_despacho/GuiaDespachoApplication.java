package com.duoc.guia_despacho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.duoc.guia_despacho")
@EntityScan("com.duoc.guia_despacho.model")
@EnableJpaRepositories("com.duoc.guia_despacho.repository")
public class GuiaDespachoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuiaDespachoApplication.class, args);
	}
}