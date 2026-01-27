package com.pividori.veterinaria.saas.iam.application.port.out;

import com.pividori.veterinaria.saas.iam.domain.model.User;

public interface UserRepositoryPort {

    User save(User user);
    
}
