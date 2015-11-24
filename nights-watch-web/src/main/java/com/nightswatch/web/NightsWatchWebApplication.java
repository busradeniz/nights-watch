package com.nightswatch.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.nightswatch")
@SpringBootApplication
public class NightsWatchWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NightsWatchWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NightsWatchWebApplication.class);
    }
}
