package ru.asanvlit.service;

import java.util.Map;

public interface EmailGenerator {

    String generateMail(String template, Map<Object, Object> data);
}

