package ru.asanvlit.util.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.asanvlit.dto.request.PostCreationRequest;
import ru.asanvlit.dto.request.PostUpdateRequest;
import ru.asanvlit.dto.response.PostResponse;
import ru.asanvlit.model.PostEntity;
import ru.asanvlit.repository.mongo.LikeRepository;
import ru.asanvlit.service.PostService;

import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {AccountMapper.class,
                FileInfoMapper.class,
                PostService.class,
                PersistenceBasicMapper.class
        },
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true))
public abstract class PostMapper {

    @Autowired
    protected LikeRepository likeRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    public abstract PostEntity toEntity(PostCreationRequest postCreationRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "artworkType", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "hoursSpent", ignore = true)
    @Mapping(target = "materials", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    public abstract PostEntity toEntity(UUID postId);

    @Mapping(target = "author", qualifiedByName = "simpleAccountResponse")
    @Mapping(target = "likesCount", ignore = true)
    public abstract PostResponse toResponse(PostEntity post);

    @AfterMapping
    protected void mapLikes(@MappingTarget PostResponse postResponse) {
        postResponse.setLikesCount(likeRepository.countByPostId(postResponse.getId()));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    public abstract void updatePost(@MappingTarget PostEntity post, PostUpdateRequest postUpdateRequest);
}

