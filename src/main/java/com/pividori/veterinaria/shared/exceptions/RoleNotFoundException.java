package com.pividori.veterinaria.shared.exceptions;

import com.pividori.veterinaria.saas.iam.domain.enums.UserRole;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(UserRole roleEnum) {
        super("Role not found: " + roleEnum);
    }
}
