package ru.asanvlit.security.oauth.provider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.asanvlit.security.oauth.details.OauthAuthentication;

@Component
public class OauthAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public OauthAuthenticationProvider(@Qualifier("accountUserDetailsOauthService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OauthAuthentication oauthAuthentication = (OauthAuthentication) authentication;

        UserDetails userDetails = userDetailsService.loadUserByUsername(oauthAuthentication.getName());
        oauthAuthentication.setAuthenticated(true);
        oauthAuthentication.setUserDetails(userDetails);

        return oauthAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OauthAuthentication.class.equals(authentication);
    }
}

