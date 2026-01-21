package com.pividori.veterinaria.onboarding.application.service;

import com.pividori.veterinaria.identity.application.port.in.RegisterUserResult;
import com.pividori.veterinaria.identity.application.port.in.RegisterUserUseCase;
import com.pividori.veterinaria.onboarding.application.mapper.RegisterCommandMapper;
import com.pividori.veterinaria.onboarding.application.port.in.RegisterCommand;
import com.pividori.veterinaria.onboarding.application.port.in.RegisterResult;
import com.pividori.veterinaria.identity.application.port.in.RegisterUseCase;
import com.pividori.veterinaria.saas.tenant.application.port.in.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.application.port.in.RegisterClinicUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {

    private final RegisterClinicUseCase registerClinicUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    @Override
    @Transactional
    public RegisterResult register(RegisterCommand registerCommand) {
        RegisterUserResult registerUserResult = registerUserUseCase.register(RegisterCommandMapper.toRegisterUserCommand(registerCommand));
        RegisterClinicResult registerClinicResult = registerClinicUseCase.register(RegisterCommandMapper.toRegisterClinicCommand(registerCommand));

        return RegisterCommandMapper.toRegisterResult(registerUserResult, registerClinicResult);
    }
}
