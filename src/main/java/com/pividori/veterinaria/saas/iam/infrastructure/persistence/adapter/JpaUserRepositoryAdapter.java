package com.pividori.veterinaria.saas.iam.infrastructure.persistence.adapter;

import com.pividori.veterinaria.saas.iam.application.port.out.UserRepositoryPort;
import com.pividori.veterinaria.saas.iam.domain.model.User;
import com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity.UserEntity;
import com.pividori.veterinaria.saas.iam.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.pividori.veterinaria.saas.iam.infrastructure.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userPersistenceMapper.toUserEntity(user);
        return userPersistenceMapper.toDomain(springDataUserRepository.save(userEntity));
    }
}
