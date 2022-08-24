package ru.asanvlit.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.asanvlit.dto.request.FileUploadRequest;
import ru.asanvlit.dto.response.FileInfoResponse;
import ru.asanvlit.model.FileInfoEntity;

import java.util.UUID;

public interface FileService {

    UUID uploadFile(FileUploadRequest file);

    FileInfoEntity getFileInfoById(UUID fileId);

    ResponseEntity<Resource> getFileResourceByFileInfoId(UUID fileId);

    FileInfoResponse getFileInfoResponse(UUID fileId);

    void deleteUnusedFiles();
}
