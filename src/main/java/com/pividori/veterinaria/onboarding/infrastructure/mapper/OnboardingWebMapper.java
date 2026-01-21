package com.pividori.veterinaria.onboarding.infrastructure.mapper;

import com.pividori.veterinaria.onboarding.application.port.in.RegisterCommand;
import com.pividori.veterinaria.onboarding.application.port.in.RegisterResult;
import com.pividori.veterinaria.onboarding.infrastructure.dto.RegisterRequest;
import com.pividori.veterinaria.onboarding.infrastructure.dto.RegisterResponse;

public final class OnboardingWebMapper {

    private OnboardingWebMapper() {
    }

    public static RegisterCommand toRegisterCommand(RegisterRequest registerRequest) {
        return new RegisterCommand(registerRequest.nameClinic(), registerRequest.timeZone(), registerRequest.name(), registerRequest.lastname(), registerRequest.email(), registerRequest.password(), registerRequest.phoneNumber(), registerRequest.dob());
    }

    public static RegisterResponse toRegisterResponse(RegisterResult registerResult) {
        return null;
    }
}
