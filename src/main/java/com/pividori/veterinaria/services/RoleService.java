package com.pividori.veterinaria.services;

import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.utility.RoleEnum;

public interface RoleService {

    Role findRoleByRoleEnum(RoleEnum roleEnum);

    Role getDefaultClientRole();

}
