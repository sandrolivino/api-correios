package br.com.livino.apicorreios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiCorreiosApplication {

	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(ApiCorreiosApplication.class, args);
	}

	public static void close(int code){
		SpringApplication.exit(ctx, () -> code);
	}
}
