package com.pividori.veterinaria.services;

import com.pividori.veterinaria.exceptions.RoleNotFoundException;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.utility.RoleEnum;
import com.pividori.veterinaria.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByRoleEnum(RoleEnum roleEnum) {
        return roleRepository.findByRoleEnum(roleEnum)
                .orElseThrow(() -> new RoleNotFoundException(roleEnum));
    }

    @Override
    public Role getDefaultClientRole() {
        return roleRepository.findByRoleEnum(RoleEnum.CLIENT)
                .orElseThrow(() -> new RoleNotFoundException(RoleEnum.CLIENT));
    }

}
