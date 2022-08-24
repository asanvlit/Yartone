package ru.asanvlit.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.asanvlit.exception.base.YartoneAccessDeniedException;
import ru.asanvlit.exception.unauthorized.YartoneAuthenticationSchemeException;
import ru.asanvlit.exception.unauthorized.YartoneJwtExpiredException;
import ru.asanvlit.security.jwt.AccountSecurityService;
import ru.asanvlit.security.provider.JwtAccessTokenProvider;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.util.request.JwtRequestUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ru.asanvlit.constant.YartoneImplConstants.AUTHENTICATION_URL;
import static ru.asanvlit.constant.YartoneImplConstants.ROLE_AUTHORITY;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final AccountService accountService;

    private final AccountSecurityService accountSecurityService;

    private final JwtAccessTokenProvider jwtAccessTokenProvider;

    public JwtAuthorizationFilter(@Qualifier("accountUserDetailsServiceImpl") UserDetailsService userDetailsService,
                                  AccountService accountService,
                                  AccountSecurityService accountSecurityService,
                                  JwtAccessTokenProvider jwtAccessTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.accountService = accountService;
        this.accountSecurityService = accountSecurityService;
        this.jwtAccessTokenProvider = jwtAccessTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTHENTICATION_URL)) {
            filterChain.doFilter(request, response);
        } else {
            if (JwtRequestUtil.hasAuthorizationToken(request)) {

                if (JwtRequestUtil.isValidTokenScheme(request.getHeader(AUTHORIZATION))) {
                    String accessToken = JwtRequestUtil.getToken(request);

                    if (!accountSecurityService.isAllowedAccount(accountService.getAccountByEmail(
                            jwtAccessTokenProvider.getEmailFromAccessToken(accessToken)).getId())) {
                        log.info("Attempt to perform an action with by account with an non-active state: {}",
                                jwtAccessTokenProvider.getEmailFromAccessToken(accessToken));
                        throw new YartoneAccessDeniedException("Can't authorize account with non-active state");
                    }

                    UsernamePasswordAuthenticationToken authenticationToken = buildAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                } else {
                    log.info("Failed to authorize token with invalid scheme");
                    throw new YartoneAuthenticationSchemeException("Invalid authorization scheme");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(String jwt) {
        if (!jwtAccessTokenProvider.isExpiredAccessToken(jwt)) {
            return new UsernamePasswordAuthenticationToken(
                    userDetailsService.loadUserByUsername(jwtAccessTokenProvider.getEmailFromAccessToken(jwt)), jwt,
                    Collections.singleton(
                            new SimpleGrantedAuthority(ROLE_AUTHORITY + jwtAccessTokenProvider.getRoleFromAccessToken(jwt))
                    )
            );
        }
        throw new YartoneJwtExpiredException("Access token expired");
    }
}

