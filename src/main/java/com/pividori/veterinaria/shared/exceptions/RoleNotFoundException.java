package com.pividori.veterinaria.shared.exceptions;

import com.pividori.veterinaria.identity.domain.Role;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Role roleEnum) {
        super("Role not found: " + roleEnum);
    }
}
