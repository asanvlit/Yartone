package ru.asanvlit.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.asanvlit.exception.methodnotallowed.YartoneTemplatesMethodException;
import ru.asanvlit.service.EmailGenerator;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class FreemarkerEmailGeneratorImpl implements EmailGenerator {

    private final FreeMarkerConfigurer configurer;

    @Override
    public String generateMail(String template, Map<Object, Object> data) {
        Template emailConfirmationTemplate = getTemplateByName(template);
        return getProceedTemplateWithData(emailConfirmationTemplate, data);
    }

    private Template getTemplateByName(String name) {
        try {
            return configurer.getConfiguration().getTemplate(name);
        } catch (IOException e) {
            log.warn(String.format("Couldn't find a template with the name: [%s]", name));
            throw new YartoneTemplatesMethodException();
        }
    }

    private String getProceedTemplateWithData(Template template, Map<Object, Object> data) {
        try (StringWriter writer = new StringWriter()) {
            template.process(data, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            log.warn(String.format("Couldn't generate a template with data due to: [%s]", e.getMessage()));
            throw new YartoneTemplatesMethodException();
        }
    }
}

