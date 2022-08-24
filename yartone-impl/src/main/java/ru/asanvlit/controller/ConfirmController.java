package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.ConfirmApi;
import ru.asanvlit.dto.request.EmailRequest;
import ru.asanvlit.dto.request.PasswordCreationRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.service.ConfirmService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ConfirmController implements ConfirmApi {

    private final ConfirmService confirmService;

    @Override
    public AccessRefreshTokenPairResponse confirmAccount(UUID confirmCode,
                                                         PasswordCreationRequest passwordCreationRequest) {
        return confirmService.confirmAccount(confirmCode, passwordCreationRequest);
    }

    @Override
    public void resendEmailAccountConfirmCode(EmailRequest emailRequest) {
        confirmService.resendEmailAccountConfirmCode(emailRequest);
    }

    @Override
    public void resetPassword(EmailRequest emailRequest) {
        confirmService.resetPassword(emailRequest);
    }

    @Override
    public AccessRefreshTokenPairResponse createPasswordAfterReset(UUID confirmCode,
                                                                   PasswordCreationRequest passwordCreationRequest) {
        return confirmService.createPasswordAfterReset(confirmCode, passwordCreationRequest);
    }
}
