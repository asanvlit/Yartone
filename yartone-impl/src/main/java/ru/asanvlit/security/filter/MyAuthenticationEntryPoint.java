package ru.asanvlit.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.asanvlit.dto.response.ExceptionResponse;
import ru.asanvlit.exception.base.YartoneUnauthorizedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(401);

        objectMapper.writeValue(res.getOutputStream(), ExceptionResponse.builder()
                .message(authException.getMessage())
                .exceptionName(YartoneUnauthorizedException.class.getSimpleName())
                .build()
        );
    }
}

