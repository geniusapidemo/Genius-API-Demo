package com.genius.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Swagger configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.genius.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo("Genius API", "API to retrieve songs using Genius API", "v1", "", new Contact("geniusapidemo@gmail.com", "", "geniusapidemo@gmail.com"), "", ""))
                .consumes(new HashSet<>(Arrays.asList(MediaType.APPLICATION_JSON_VALUE)))
                .produces(new HashSet<>(Arrays.asList(MediaType.APPLICATION_JSON_VALUE)));
        return docket;
    }
}