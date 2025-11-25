package com.pividori.veterinaria.exceptions;

import com.pividori.veterinaria.models.utility.RoleEnum;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(RoleEnum roleEnum) {
        super("Role not found: " + roleEnum);
    }
}
