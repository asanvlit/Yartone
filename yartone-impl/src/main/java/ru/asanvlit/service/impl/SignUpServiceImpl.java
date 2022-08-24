package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.AccountRole;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.dto.request.SignUpRequest;
import ru.asanvlit.exception.illegalstate.YartoneAlreadyRegisteredEmailException;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.service.ConfirmService;
import ru.asanvlit.service.SignUpService;
import ru.asanvlit.util.mapper.AccountMapper;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final AccountService accountService;

    private final ConfirmService confirmService;

    private final AccountMapper accountMapper;

    @Override
    public UUID signUp(SignUpRequest signUpRequest) {
        if (accountService.existsAccountByEmail(signUpRequest.getEmail())) {
            throw new YartoneAlreadyRegisteredEmailException();
        }

        AccountEntity account = accountMapper.signUpFormToEntity(signUpRequest);
        account.setState(AccountState.CREATED);
        account.setRole(AccountRole.USER);
        account.setCreateDate(Instant.now());

        UUID id = accountService.saveAccount(account).getId();

        UUID confirmCodeValue = confirmService.generateConfirmCodeForAccount(account.getId(), ConfirmCodeType.ACCOUNT_CONFIRM);
        confirmService.sendAccountConfirm(account.getId(), confirmCodeValue);

        return id;
    }
}

