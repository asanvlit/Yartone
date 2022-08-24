package ru.asanvlit.security.jwt.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.security.jwt.AccountSecurityService;
import ru.asanvlit.service.AccountService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountSecurityServiceImpl implements AccountSecurityService {

    private final AccountService accountService;

    @Override
    public boolean isAllowedAccount(UUID accountId) {
        return AccountState.ACTIVE.equals(accountService.getAccountById(accountId).getState());
    }
}
