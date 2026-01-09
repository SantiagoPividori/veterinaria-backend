package com.pividori.veterinaria.shared.exceptions;

import com.pividori.veterinaria.clinic.user.domain.RoleEnum;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(RoleEnum roleEnum) {
        super("Role not found: " + roleEnum);
    }
}
