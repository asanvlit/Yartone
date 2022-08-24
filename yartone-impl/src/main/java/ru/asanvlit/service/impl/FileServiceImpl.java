package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.asanvlit.dto.enums.FileType;
import ru.asanvlit.dto.request.FileUploadRequest;
import ru.asanvlit.dto.response.FileInfoResponse;
import ru.asanvlit.exception.illegalargument.YartoneInvalidFileExtensionException;
import ru.asanvlit.exception.notfound.YartoneFileNotFoundException;
import ru.asanvlit.model.FileInfoEntity;
import ru.asanvlit.repository.postgres.FileInfoRepository;
import ru.asanvlit.service.FileContentService;
import ru.asanvlit.service.FileService;
import ru.asanvlit.util.mapper.FileInfoMapper;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static ru.asanvlit.constant.YartoneImplConstants.EXTENSIONS_FOR_FILE_TYPES;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileContentService fileContentService;

    private final FileInfoRepository fileInfoRepository;

    private final FileInfoMapper fileInfoMapper;

    @Transactional
    @Override
    public UUID uploadFile(FileUploadRequest file) {
        if (isAcceptableFileExtension(file.getFileType(), file.getFileContent())) {
            return saveFileInfo(file.getFileType(), fileContentService.uploadFile(file.getFileContent()),
                    file.getFileContent()).getId();
        } else {
            throw new YartoneInvalidFileExtensionException();
        }
    }

    @Override
    public FileInfoEntity getFileInfoById(UUID fileId) {
        return fileInfoRepository.findById(fileId).orElseThrow(YartoneFileNotFoundException::new);
    }

    @Override
    public ResponseEntity<Resource> getFileResourceByFileInfoId(UUID fileId) {
        return fileContentService.getFileResourceById(getFileInfoById(fileId).getStorageName());
    }

    @Override
    public FileInfoResponse getFileInfoResponse(UUID fileId) {
        return fileInfoMapper.toResponse(getFileInfoById(fileId));
    }

    @Override
    public void deleteUnusedFiles() {
        fileInfoRepository.getUnusedFilesIdSet().stream()
                .filter(Objects::nonNull)
                .map(this::getFileInfoById)
                .map(f -> {fileContentService.removeFileContent(f.getStorageName()); return f;})
                .map(f -> {fileInfoRepository.delete(f); return f;})
                .forEach(f -> log.info(String.format("Deleted file-info with id: [%s] and it's content with id: [%s]",
                        f.getId().toString(), f.getStorageName())));
    }

    private FileInfoEntity saveFileInfo(FileType type, String storageName, MultipartFile file) {
        return fileInfoRepository.save(FileInfoEntity.builder()
                .contentType(file.getContentType())
                .fileType(type)
                .originalName(file.getOriginalFilename())
                .storageName(storageName)
                .size(file.getSize())
                .build()
        );
    }

    private boolean isAcceptableFileExtension(FileType fileType, MultipartFile multipart) {
        return Arrays.asList(EXTENSIONS_FOR_FILE_TYPES.get(fileType))
                .contains(getFileExtension(multipart));
    }

    private String getFileExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).split("/")[1];
    }
}
