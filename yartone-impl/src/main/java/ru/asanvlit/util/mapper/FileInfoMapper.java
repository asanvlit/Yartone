package ru.asanvlit.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.asanvlit.dto.response.FileInfoResponse;
import ru.asanvlit.model.FileInfoEntity;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FileInfoMapper {

    FileInfoResponse toResponse(FileInfoEntity fileInfo);
}
