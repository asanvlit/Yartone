package ru.asanvlit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = {"ru.asanvlit.repository.mongo"})
@EnableJpaRepositories(basePackages = {"ru.asanvlit.repository.postgres"})
@Configuration
public class DatabaseConfig {
}
