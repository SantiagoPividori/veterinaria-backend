package com.pividori.veterinaria.saas.iam.application.port.in;

import com.pividori.veterinaria.onboarding.application.port.in.RegisterCommand;
import com.pividori.veterinaria.onboarding.application.port.in.RegisterResult;

public interface RegisterUseCase {

    public RegisterResult register(RegisterCommand registerCommand);

}
