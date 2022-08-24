package ru.asanvlit.validation.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class MessageConfig {

    private final Environment environment;

    public MessageConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(Objects.requireNonNull(environment.getProperty("messages.sourse.path")));
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }
}
