package ru.asanvlit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.nio.charset.StandardCharsets;

import static ru.asanvlit.constant.YartoneImplConstants.CLASSPATH;

@Configuration
@PropertySource("classpath:application.properties")
public class FreemarkerConfig {

    private final Environment environment;

    public FreemarkerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configurer.setTemplateLoaderPath(CLASSPATH + environment.getProperty("templates.path"));
        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }
}

