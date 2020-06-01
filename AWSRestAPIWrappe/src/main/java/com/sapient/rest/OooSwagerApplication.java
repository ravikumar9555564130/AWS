package com.sapient.rest;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OooSwagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OooSwagerApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.sapient.rest")).build().apiInfo(apiInfo());

	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Sapient OOO Application to Upload File", 
	      "This API used to upload Excel file in OOO Application.", 
	      "API OOO", 
	      "Terms of service", 
	       new Contact("publiciessapient", "https://www.publicissapient.com/", "ravi.kumar3@publicissapient.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}

}
