package ru.asanvlit.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.asanvlit.security.filter.FilterExceptionHandler;
import ru.asanvlit.security.filter.JwtAuthenticationFilter;
import ru.asanvlit.security.filter.JwtAuthorizationFilter;
import ru.asanvlit.security.filter.MyAuthenticationEntryPoint;
import ru.asanvlit.security.oauth.filter.OauthAuthenticationFilter;
import ru.asanvlit.security.oauth.provider.OauthAuthenticationProvider;

import static ru.asanvlit.constant.YartoneImplConstants.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final MyAuthenticationEntryPoint entryPoint;

    private final OauthAuthenticationProvider oauthAuthenticationProvider;

    public static final String[] PERMIT_ALL = {
            AUTHENTICATION_URL,
            SIGN_UP_URL,
            ACCOUNT_CONFIRM_URL,
            RESEND_ACCOUNT_CONFIRM_CODE_URL,
            PASSWORD_RESET,
            REFRESH_TOKEN_URL,
            USER_INFO_BY_TOKEN_URL,
            VK_OAUTH_ALL_LINKS
    };

    public static final String[] IGNORE = {
            "/account-swagger/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui",
            "/swagger-ui/**",
            "/swagger-ui.html/**"
    };

    public WebSecurityConfig(@Qualifier("accountUserDetailsServiceImpl") UserDetailsService userDetailsService,
                             PasswordEncoder passwordEncoder,
                             MyAuthenticationEntryPoint authenticationEntryPoint,
                             OauthAuthenticationProvider oauthAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.entryPoint = authenticationEntryPoint;
        this.oauthAuthenticationProvider = oauthAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   JwtAuthorizationFilter jwtAuthorizationFilter,
                                                   OauthAuthenticationFilter oauthAuthenticationFilter,
                                                   FilterExceptionHandler filterExceptionHandler) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .csrf().disable();

        httpSecurity.addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(oauthAuthenticationFilter, JwtAuthorizationFilter.class)
                .addFilterBefore(filterExceptionHandler, OauthAuthenticationFilter.class);

        httpSecurity.authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint);

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(IGNORE);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        builder.authenticationProvider(oauthAuthenticationProvider);
    }
}

