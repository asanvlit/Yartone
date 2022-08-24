package ru.asanvlit.util.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.asanvlit.dto.response.LikeResponse;
import ru.asanvlit.model.mongo.LikeEntity;

@Mapper(componentModel = "spring",
        uses = {
            AccountMapper.class
        },
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true))
public abstract class LikeMapper {

    @Autowired
    protected AccountMapper accountMapper;

    @Mapping(target = "account", ignore = true)
    public abstract LikeResponse toResponse(LikeEntity like);

    @AfterMapping
    protected void mapAccountAndPost(@MappingTarget LikeResponse likeResponse, LikeEntity like) {
        likeResponse.setAccount(accountMapper.toResponse(accountMapper.toEntity(like.getAccountId())));
    }
}

