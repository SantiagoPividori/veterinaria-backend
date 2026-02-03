package com.pividori.veterinaria.saas.tenant.application.service;

import com.pividori.veterinaria.saas.tenant.application.port.out.RegisterUserPort;
import com.pividori.veterinaria.saas.tenant.application.port.in.command.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.result.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.application.port.in.usecase.RegisterClinicUseCase;
import com.pividori.veterinaria.saas.tenant.application.port.out.ClinicRepositoryPort;
import com.pividori.veterinaria.saas.tenant.domain.enums.PlanType;
import com.pividori.veterinaria.saas.tenant.domain.model.Clinic;
import com.pividori.veterinaria.saas.tenant.domain.valueobject.*;
import com.pividori.veterinaria.shared.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterClinicService implements RegisterClinicUseCase {

    private final ClinicRepositoryPort clinicRepositoryPort;
    private final RegisterUserPort registerUserPort;

    @Override
    @Transactional
    public RegisterClinicResult execute(RegisterClinicCommand registerClinicCommand) {
        //Create user
        UserId userId = registerUserPort.registerUser(registerClinicCommand.ownerEmail(),
                registerClinicCommand.ownerPassword(),
                registerClinicCommand.ownerFirstName(),
                registerClinicCommand.ownerLastName(),
                registerClinicCommand.ownerBirthDate());

        //Create clinic
        Clinic clinic = Clinic.create(new ClinicName(registerClinicCommand.name()),
                new ClinicLegalName(registerClinicCommand.legalName()),
                new ClinicCuit(registerClinicCommand.cuit()),
                new ClinicEmail(registerClinicCommand.email()),
                new ClinicPhoneNumber(registerClinicCommand.phoneNumber()),
                new ClinicAddress(registerClinicCommand.street(),
                        registerClinicCommand.city(),
                        registerClinicCommand.province(),
                        registerClinicCommand.postalCode()),
                PlanType.valueOf(registerClinicCommand.planType()),
                userId
        );

        Clinic clinicSaved = clinicRepositoryPort.save(clinic);
        return new RegisterClinicResult(clinicSaved.getId());
    }
}