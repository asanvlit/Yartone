package ru.asanvlit.util.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.service.ConfirmService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfirmCodeCleaningUtil {

    private final AccountService accountService;

    private final ConfirmService confirmService;

    @Scheduled(cron = "@daily")
    @Async
    public void deleteExpiredUnconfirmedAccounts() {
        log.info("Start checking the database for expired unconfirmed accounts...");

        accountService.getExpiredUnconfirmedAccounts().stream()
                .map(a -> {accountService.deleteAccount(a.getId()); return a;})
                .forEach(a -> log.info(String.format("Deleted account with id : [%s]", a.getId())));

        log.info("Stopped checking the database for expired unconfirmed accounts.");
    }

    @Scheduled(cron = "@daily")
    @Async
    public void deleteExpiredConfirmCodes() {
        log.info("Start checking the database for expired confirm codes...");

        confirmService.getExpiredConfirmCodes().stream()
                .filter(c -> !ConfirmCodeType.ACCOUNT_CONFIRM.equals(c.getConfirmCodeType().getConfirmCodeType()))
                .map(c -> {confirmService.deleteConfirmCode(c.getId()); return c;})
                .forEach(a -> log.info(String.format("Deleted confirm-code with id : [%s]", a.getId())));

        log.info("Stopped checking the database for expired confirm codes.");
    }
}
