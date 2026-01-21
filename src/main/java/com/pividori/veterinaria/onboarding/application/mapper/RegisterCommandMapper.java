package com.pividori.veterinaria.onboarding.application.mapper;

import com.pividori.veterinaria.identity.application.port.in.RegisterUserCommand;
import com.pividori.veterinaria.identity.application.port.in.RegisterUserResult;
import com.pividori.veterinaria.onboarding.application.port.in.RegisterCommand;
import com.pividori.veterinaria.onboarding.application.port.in.RegisterResult;
import com.pividori.veterinaria.saas.tenant.application.port.in.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.RegisterClinicResult;

public final class RegisterCommandMapper {

    private RegisterCommandMapper(){
    }

    public static RegisterUserCommand toRegisterUserCommand(RegisterCommand registerCommand) {
        return new RegisterUserCommand(registerCommand.name(), registerCommand.lastname(), registerCommand.email(), registerCommand.password(), registerCommand.phoneNumber(), registerCommand.dob());
    }

    public static RegisterClinicCommand toRegisterClinicCommand(RegisterCommand registerCommand) {
        return new RegisterClinicCommand(registerCommand.clinicName(), registerCommand.timeZone());
    }

    public static RegisterResult toRegisterResult(RegisterUserResult registerUserResult, RegisterClinicResult registerClinicResult) {

    }
}
