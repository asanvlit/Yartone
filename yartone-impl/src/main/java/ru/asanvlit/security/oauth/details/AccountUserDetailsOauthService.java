package ru.asanvlit.security.oauth.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.asanvlit.security.details.AccountUserDetailsImpl;
import ru.asanvlit.security.oauth.vk.VkOauthService;

@RequiredArgsConstructor
@Service
public class AccountUserDetailsOauthService implements UserDetailsService {

    private final VkOauthService oauthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AccountUserDetailsImpl(oauthService.getVkAccount(username));
    }
}

