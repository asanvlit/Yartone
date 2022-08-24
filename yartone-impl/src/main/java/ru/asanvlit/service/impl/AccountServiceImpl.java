package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.dto.enums.FileType;
import ru.asanvlit.dto.request.AccountChangePasswordRequest;
import ru.asanvlit.dto.request.AccountUpdateRequest;
import ru.asanvlit.dto.request.FileRequest;
import ru.asanvlit.dto.response.AccountExtendedResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.exception.illegalargument.YartoneIllegalPasswordException;
import ru.asanvlit.exception.illegalargument.YartoneInvalidFileTypeException;
import ru.asanvlit.exception.notfound.YartoneAccountNotFoundException;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.model.FileInfoEntity;
import ru.asanvlit.repository.postgres.AccountRepository;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.service.FileService;
import ru.asanvlit.util.mapper.AccountMapper;
import ru.asanvlit.util.string.FromToTransliteration;
import ru.asanvlit.util.string.StringTransliterator;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final FileService fileService;

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsAccountByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean existsAccountById(UUID accountId) {
        return accountRepository.existsById(accountId);
    }

    @Override
    public AccountEntity saveAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public AccountEntity getAccountById(UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow(YartoneAccountNotFoundException::new);
    }

    @Override
    public AccountEntity getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(YartoneAccountNotFoundException::new);
    }

    @Override
    public void changeAccountsPassword(UUID accountId, String password) {
        AccountEntity account = getAccountById(accountId);
        account.setPasswordHash(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    @Override
    public void changeAccountsState(UUID accountId, AccountState state) {
        AccountEntity account = getAccountById(accountId);
        account.setState(state);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        accountRepository.delete(getAccountById(accountId));
    }

    @Override
    public Optional<AccountEntity> findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Set<AccountEntity> getExpiredUnconfirmedAccounts() {
        return accountRepository.getExpiredUnconfirmedAccounts();
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String name = firstName.concat(StringUtils.SPACE).concat(lastName);
        String username = StringTransliterator.transliterate(FromToTransliteration.CYRILLIC_TO_LATIN,
                name.replaceAll(StringUtils.SPACE, "_").toLowerCase());

        String newUsername = username;
        int i = 0;
        while (accountRepository.existsByUsername(newUsername)) {
            newUsername = username.concat(String.valueOf(i++));
        }

        return newUsername;
    }

    @Override
    public Optional<AccountResponse> findBySubject(String subjectFromAccessToken) {
        return accountRepository.findByEmail(subjectFromAccessToken)
                .map(accountMapper::toResponse);
    }

    @Override
    public Page<AccountResponse> getAccountsMainInfoPage(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toResponse);
    }

    @Override
    public AccountResponse updateAccountsPassword(UUID accountId, AccountChangePasswordRequest changePasswordRequest) {
        AccountEntity account = getAccountById(accountId);

        if (passwordEncoder.matches(changePasswordRequest.getPreviousPassword(), account.getPasswordHash())) {
            account.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getPassword()));

            return accountMapper.toResponse(accountRepository.save(account));
        } else {
            throw new YartoneIllegalPasswordException("The entered password doesn't match the previous password");
        }
    }

    @Override
    public AccountExtendedResponse getAccountExtendedResponseById(UUID accountId) {
        return accountMapper.toExtendedResponse(getAccountById(accountId));
    }

    @Override
    public AccountResponse getAccountResponse(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toResponse)
                .orElseThrow(YartoneAccountNotFoundException::new);
    }

    @Override
    public AccountExtendedResponse updateAccountsMainInfo(UUID accountId, AccountUpdateRequest accountUpdateRequest) {
        return accountRepository.findById(accountId)
                .map(a -> {accountMapper.updateAccount(a, accountUpdateRequest); return a;})
                .map(accountRepository::save)
                .map(accountMapper::toExtendedResponse)
                .orElseThrow(YartoneAccountNotFoundException::new);
    }

    @Override
    public AccountExtendedResponse updateAccountsProfilePhoto(UUID accountId, FileRequest fileRequest) {
        FileInfoEntity updatedProfilePhoto = fileService.getFileInfoById(fileRequest.getId());
        if (!FileType.PROFILE_PHOTO.equals(updatedProfilePhoto.getFileType())) {
            throw new YartoneInvalidFileTypeException("Not acceptable file type");
        }

        return accountRepository.findById(accountId)
                .map(a -> {a.setProfilePhoto(updatedProfilePhoto); return a;})
                .map(accountRepository::save)
                .map(accountMapper::toExtendedResponse)
                .orElseThrow(YartoneAccountNotFoundException::new);
    }
}

