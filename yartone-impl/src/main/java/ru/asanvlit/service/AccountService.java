package ru.asanvlit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.dto.request.AccountChangePasswordRequest;
import ru.asanvlit.dto.request.AccountUpdateRequest;
import ru.asanvlit.dto.request.FileRequest;
import ru.asanvlit.dto.response.AccountExtendedResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.model.AccountEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AccountService {

    boolean existsAccountByEmail(String email);

    boolean existsAccountById(UUID accountId);

    AccountEntity saveAccount(AccountEntity account);

    AccountEntity getAccountById(UUID accountId);

    AccountEntity getAccountByEmail(String email);

    void changeAccountsPassword(UUID accountId, String password);

    void changeAccountsState(UUID accountId, AccountState state);

    void deleteAccount(UUID accountId);

    Optional<AccountEntity> findAccountByEmail(String email);

    Set<AccountEntity> getExpiredUnconfirmedAccounts();

    String generateUsername(String firstName, String lastName);

    Optional<AccountResponse> findBySubject(String subjectFromAccessToken);

    Page<AccountResponse> getAccountsMainInfoPage(Pageable pageable);

    AccountResponse updateAccountsPassword(UUID accountId, AccountChangePasswordRequest changePasswordRequest);

    AccountExtendedResponse getAccountExtendedResponseById(UUID accountId);

    AccountResponse getAccountResponse(UUID accountId);

    AccountExtendedResponse updateAccountsMainInfo(UUID accountId, AccountUpdateRequest accountUpdateRequest);

    AccountExtendedResponse updateAccountsProfilePhoto(UUID accountId, FileRequest fileRequest);
}
