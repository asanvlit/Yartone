package ru.asanvlit.security.oauth.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.asanvlit.security.oauth.details.OauthAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.asanvlit.constant.YartoneImplConstants.*;

@Component
public class OauthAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        if (request.getRequestURI().equals(VK_OAUTH_LOGIN_REDIRECT_URL)) {
            SecurityContextHolder.getContext().setAuthentication(
                    new OauthAuthentication(request.getParameter(OAUTH_CODE_PARAM))
            );
        }

        chain.doFilter(request, response);
    }
}
