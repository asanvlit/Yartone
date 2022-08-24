package ru.asanvlit.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
public class MinioConfig {

    private final Environment environment;

    public MinioConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(Objects.requireNonNull(environment.getProperty("data.minio.endpoint")))
                .credentials(Objects.requireNonNull(environment.getProperty("data.minio.username")),
                             Objects.requireNonNull(environment.getProperty("data.minio.password")))
                .build();
    }
}
