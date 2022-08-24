package ru.asanvlit.security.oauth.details;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class OauthAuthentication implements Authentication {

    private final String code;

    private boolean isAuthenticated;

    private UserDetails userDetails;

    public OauthAuthentication(String code) {
        this.code = code;
        this.isAuthenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails != null ? userDetails.getAuthorities() : null;
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return code;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}

