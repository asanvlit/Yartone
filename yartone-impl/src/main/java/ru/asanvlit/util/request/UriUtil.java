package ru.asanvlit.util.request;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

@UtilityClass
public class UriUtil {

    public String buildRequest(String host, Map<String, String> paramsMap) {
        return host + "?" + buildParametersPart(paramsMap);
    }

    public String buildParametersPart(Map<String, String> paramsMap)  {
        StringBuilder parameters = new StringBuilder();

        paramsMap.forEach((key, value) -> parameters.append(key)
                .append("=")
                .append(value)
                .append("&"));
        parameters.deleteCharAt(parameters.length() - 1);

        return parameters.toString();
    }

    public String buildMultipleParametersValue(Collection<?> values) {
        StringBuilder result = new StringBuilder();
        values.forEach(v -> result.append(",").append(v.toString()));

        return result.substring(1);
    }
}

