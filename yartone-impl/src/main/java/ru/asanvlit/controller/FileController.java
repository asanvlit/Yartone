package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.FileApi;
import ru.asanvlit.dto.request.FileUploadRequest;
import ru.asanvlit.dto.response.FileInfoResponse;
import ru.asanvlit.service.FileService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class FileController implements FileApi {

    private final FileService fileService;

    @Override
    public UUID uploadFile(FileUploadRequest file) {
        return fileService.uploadFile(file);
    }

    @Override
    public ResponseEntity<Resource> getFileResource(UUID fileId) {
        return fileService.getFileResourceByFileInfoId(fileId);
    }

    @Override
    public FileInfoResponse getFileInfo(UUID fileId) {
        return fileService.getFileInfoResponse(fileId);
    }
}
