package ru.asanvlit.util.mapper;

import org.mapstruct.*;
import ru.asanvlit.dto.request.AccountUpdateRequest;
import ru.asanvlit.dto.request.SignUpRequest;
import ru.asanvlit.dto.response.AccountExtendedResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.model.AccountEntity;

import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {
                PostMapper.class,
                FileInfoMapper.class,
                PersistenceBasicMapper.class
        },
        builder = @Builder(disableBuilder = true),
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "profilePhoto", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "codes", ignore = true)
    AccountEntity signUpFormToEntity(SignUpRequest signUpRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "profilePhoto", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "codes", ignore = true)
    AccountEntity toEntity(UUID accountId);

    @Named(value = "simpleAccountResponse")
    AccountResponse toResponse(AccountEntity account);

    AccountExtendedResponse toExtendedResponse(AccountEntity account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "profilePhoto", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "codes", ignore = true)
    @Mapping(target = "posts", ignore = true)
    void updateAccount(@MappingTarget AccountEntity account, AccountUpdateRequest accountInfoUpdateRequest);
}

