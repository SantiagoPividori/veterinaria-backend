package com.pividori.veterinaria.saas.tenant.application.service;

import com.pividori.veterinaria.saas.tenant.application.port.in.command.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.result.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.application.port.in.usecase.RegisterClinicUseCase;
import com.pividori.veterinaria.saas.tenant.application.port.out.ClinicRepositoryPort;
import com.pividori.veterinaria.saas.tenant.domain.model.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RegisterClinicService implements RegisterClinicUseCase {

    private final ClinicRepositoryPort clinicRepositoryPort;
    private final Clock clock;

    @Override
    @Transactional
    public RegisterClinicResult register(RegisterClinicCommand registerClinicCommand) {
        Instant now = Instant.now(clock);
        Clinic clinicSaved = clinicRepositoryPort.save(Clinic.register(registerClinicCommand.name(), now));
        return new RegisterClinicResult(clinicSaved.getId());
    }
}