package ru.asanvlit.util.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.asanvlit.exception.methodnotallowed.YartoneFileException;
import ru.asanvlit.service.FileService;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileCleaningUtil {

    private final FileService fileService;

    @Scheduled(cron = "@daily")
    @Async
    public void deleteUnusedFiles() {
        log.info("Start checking the database for unused files...");
        try {
            fileService.deleteUnusedFiles();
        } catch (YartoneFileException e) {
            log.warn(String.format("Could not clean the database files due to : %s", e.getMessage()));
        }
        log.info("Stopped checking the database for unused files.");
    }
}

