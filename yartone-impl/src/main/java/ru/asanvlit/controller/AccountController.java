package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.AccountApi;
import ru.asanvlit.dto.request.AccountChangePasswordRequest;
import ru.asanvlit.dto.request.AccountUpdateRequest;
import ru.asanvlit.dto.request.FileRequest;
import ru.asanvlit.dto.response.AccountExtendedResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.security.details.AccountUserDetailsImpl;
import ru.asanvlit.service.AccountService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AccountController implements AccountApi<AccountUserDetailsImpl> {

    private final AccountService accountService;

    @Override
    public AccountExtendedResponse getAccountExtendedInfo(UUID accountId) {
        return accountService.getAccountExtendedResponseById(accountId);
    }

    @Override
    public Page<AccountResponse> getAccountsMainInfoPage(Pageable pageable) {
        return accountService.getAccountsMainInfoPage(pageable);
    }

    @Override
    public AccountResponse updateAccountsPassword(@AuthenticationPrincipal AccountUserDetailsImpl user,
                                                  AccountChangePasswordRequest changePasswordRequest) {
        return accountService.updateAccountsPassword(user.getAccount().getId(), changePasswordRequest);
    }

    @Override
    public AccountExtendedResponse updateAccountsMainInfo(@AuthenticationPrincipal AccountUserDetailsImpl user,
                                                          AccountUpdateRequest accountUpdateRequest) {
        return accountService.updateAccountsMainInfo(user.getAccount().getId(), accountUpdateRequest);
    }

    @Override
    public AccountExtendedResponse updateAccountsProfilePhoto(@AuthenticationPrincipal AccountUserDetailsImpl user,
                                                              FileRequest fileRequest) {
        return accountService.updateAccountsProfilePhoto(user.getAccount().getId(), fileRequest);
    }
}
