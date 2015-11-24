package com.nightswatch.dal.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.nightswatch.dal.entity"})
@EnableJpaRepositories(basePackages = {"com.nightswatch.dal.repository"})
public class DalConfiguration {

}
