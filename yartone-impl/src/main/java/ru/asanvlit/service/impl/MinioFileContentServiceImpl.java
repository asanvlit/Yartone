package ru.asanvlit.service.impl;

import io.minio.*;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.asanvlit.exception.methodnotallowed.YartoneFileException;
import ru.asanvlit.service.FileContentService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.UUID;

import static ru.asanvlit.constant.YartoneImplConstants.CONTENT_TYPE_HEADER;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class MinioFileContentServiceImpl implements FileContentService {

    private final MinioClient minioClient;

    @Value("${data.minio.bucket}")
    private String bucketName;

    @Override
    public ResponseEntity<Resource> getFileResourceById(String id) {
        try {
            GetObjectResponse file = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(id)
                    .build()
            );

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(Objects.requireNonNull(file.headers().get(CONTENT_TYPE_HEADER))))
                    .body(new InputStreamResource(file));
        } catch (IOException | MinioException ex) {
            log.warn("Failed to get file {} due to the exception: {}", id, ex.getMessage());
            throw new YartoneFileException("Couldn't download file due to storage error");
        } catch (GeneralSecurityException ex) {
            log.warn("Failed to get file {} due to security exception: {}", id, ex.getMessage());
            throw new YartoneFileException("Couldn't download file due to failed security");
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            return minioClient.putObject(PutObjectArgs.builder()
                    .object(UUID.randomUUID().toString())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .bucket(bucketName)
                    .build()).object();
        } catch (IOException | MinioException ex) {
            log.warn("Failed to upload file due to the exception: {}", ex.getMessage());
            throw new YartoneFileException("Couldn't upload file due to storage error");
        } catch (GeneralSecurityException ex) {
            log.warn("Failed to upload file due to security exception: {}", ex.getMessage());
            throw new YartoneFileException("Couldn't upload file due to failed security");
        }
    }

    @Override
    public void removeFileContent(String storageName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(storageName)
                            .build());
        } catch (IOException | MinioException ex) {
            log.warn("Failed to delete file {} due to the exception: {}", storageName, ex.getMessage());
            throw new YartoneFileException("Couldn't delete file due to storage error");
        } catch (GeneralSecurityException ex) {
            log.warn("Failed to delete file {} due to security exception: {}", storageName, ex.getMessage());
            throw new YartoneFileException("Couldn't delete file due to failed security");
        }
    }
}
