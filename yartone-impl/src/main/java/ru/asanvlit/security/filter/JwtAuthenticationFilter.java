package ru.asanvlit.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.asanvlit.dto.request.EmailPasswordRequest;
import ru.asanvlit.exception.base.YartoneAccessDeniedException;
import ru.asanvlit.exception.base.YartoneIllegalArgumentException;
import ru.asanvlit.exception.illegalstate.YartoneAlreadyLoggedException;
import ru.asanvlit.exception.illegalstate.YartoneRefreshTokenStateException;
import ru.asanvlit.exception.notfound.YartoneAccountNotFoundException;
import ru.asanvlit.exception.unauthorized.YartoneIllegalAuthenticationDataException;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.security.details.AccountUserDetailsImpl;
import ru.asanvlit.security.jwt.AccountSecurityService;
import ru.asanvlit.security.jwt.JwtService;
import ru.asanvlit.service.AccountService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static ru.asanvlit.constant.YartoneImplConstants.AUTHENTICATION_URL;
import static ru.asanvlit.constant.YartoneImplConstants.USERNAME_PARAM;

@Slf4j
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AccountService accountService;

    private final AccountSecurityService accountSecurityService;

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(ObjectMapper objectMapper,
                                   AccountService accountService,
                                   AccountSecurityService accountSecurityService,
                                   JwtService jwtService,
                                   AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setFilterProcessesUrl(AUTHENTICATION_URL);
        this.setUsernameParameter(USERNAME_PARAM);
        this.accountService = accountService;
        this.accountSecurityService = accountSecurityService;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        EmailPasswordRequest emailPassword = null;
        try {
            emailPassword = objectMapper.readValue(request.getReader(), EmailPasswordRequest.class);
        } catch (IOException e) {
            log.warn("Failed to attempt authentication due to: {}", e.getMessage());
            throw new YartoneIllegalArgumentException("Failed authentication attempt");
        }

        if (Objects.isNull(emailPassword.getEmail()) || Objects.isNull(emailPassword.getPassword())) {
            throw new YartoneIllegalAuthenticationDataException("Email and password can't be null");
        }
        log.info("Attempt to authenticate using email: {}", emailPassword.getEmail());


        try {
            if (!accountSecurityService.isAllowedAccount(accountService.getAccountByEmail(emailPassword.getEmail()).getId())) {
                log.info("Attempt to authenticate using account with non-active state: {}", emailPassword.getEmail());
                throw new YartoneAccessDeniedException("Can't authenticate account with non-active state");
            }

            return super.getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(emailPassword.getEmail(), emailPassword.getPassword())
            );
        } catch (YartoneAccountNotFoundException | AuthenticationException e) {
            throw new YartoneIllegalAuthenticationDataException("Illegal email or password");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        AccountUserDetailsImpl accountUserDetails = (AccountUserDetailsImpl) authResult.getPrincipal();
        writeTokensToResponse(response, accountUserDetails.getAccount());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void writeTokensToResponse(HttpServletResponse response, AccountEntity account) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            objectMapper.writeValue(response.getOutputStream(),
                    jwtService.getAccessRefreshTokenPair(account.getId()));
        } catch (YartoneRefreshTokenStateException e) {
            throw new YartoneAlreadyLoggedException("The login has already been");
        } catch (IOException e) {
            log.warn(String.format("Failed to write tokens to response due to: %s", e.getMessage()));
            throw new YartoneIllegalArgumentException("Failed authentication attempt");
        }
    }
}

