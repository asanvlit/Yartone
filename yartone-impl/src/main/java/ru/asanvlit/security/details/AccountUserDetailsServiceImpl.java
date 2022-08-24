package ru.asanvlit.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.asanvlit.service.AccountService;

@RequiredArgsConstructor
@Service
public class AccountUserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AccountUserDetailsImpl(
                accountService.findAccountByEmail(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(String.format("The user with the email [%s] was not found", email))
                        )
        );
    }
}

