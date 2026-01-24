package com.pividori.veterinaria.saas.tenant.application.port.in.usecase;

import com.pividori.veterinaria.saas.tenant.application.port.in.command.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.result.RegisterClinicResult;

public interface RegisterClinicUseCase {
    RegisterClinicResult register(RegisterClinicCommand registerClinicCommand);
}
