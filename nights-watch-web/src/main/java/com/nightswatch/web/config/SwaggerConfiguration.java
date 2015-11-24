package com.nightswatch.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String PATTERN = "/.*";

    @Bean
    public Docket swaggerSpringfoxDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ApiInfo.DEFAULT)
                .genericModelSubstitutes(ResponseEntity.class)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .paths(regex(PATTERN))
                .build();

        return docket;
    }
}