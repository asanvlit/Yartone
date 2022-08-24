package ru.asanvlit.security.jwt;

import java.util.UUID;

public interface AccountSecurityService {

    boolean isAllowedAccount(UUID accountId);
}
