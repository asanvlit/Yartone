package ru.asanvlit.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileContentService {
    ResponseEntity<Resource> getFileResourceById(String id);

    String uploadFile(MultipartFile file);

    void removeFileContent(String storageName);
}
