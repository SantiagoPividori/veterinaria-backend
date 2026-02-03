package com.pividori.veterinaria.saas.iam.infrastructure.persistence.mapper;

import com.pividori.veterinaria.saas.iam.domain.model.User;
import com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    @Mapping(source = "nashe", target = "nashe")
    default UserEntity toUserEntity(User user) {
        return null;
    }

    default User toDomain(UserEntity userEntity) {
        return null;
    }
}
