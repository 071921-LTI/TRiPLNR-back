package com.lti.triplnr20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lti.triplnr20.services.WeatherService;
import com.lti.triplnr20.services.WeatherServiceImpl;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
//		WeatherService ws = new WeatherServiceImpl();
//		System.out.println(ws.getCurrentWeather("Uncasville,CT"));
//		System.out.println(ws.getDestinationWeather("Uncasville,CT","1"));
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

}
