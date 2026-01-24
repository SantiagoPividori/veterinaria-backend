package com.pividori.veterinaria.saas.tenant.infrastructure.rest.controller;

import com.pividori.veterinaria.saas.tenant.application.port.in.command.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.result.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.mapper.ClinicWebMapper;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto.RegisterClinicRequest;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto.RegisterClinicResponse;
import com.pividori.veterinaria.saas.tenant.application.port.in.usecase.RegisterClinicUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping("api/v1/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final RegisterClinicUseCase registerClinicUseCase;
    private final ClinicWebMapper clinicWebMapper;

    @PostMapping
    public ResponseEntity<RegisterClinicResponse> registerClinic(@Valid @RequestBody RegisterClinicRequest registerClinicRequest) {

        RegisterClinicCommand registerClinicCommand = clinicWebMapper.toRegisterClinicCommand(registerClinicRequest);

        RegisterClinicResult registerClinicResult = registerClinicUseCase.register(registerClinicCommand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registerClinicResult.clinicId()) // Usamos el ID del resultado
                .toUri();

        return ResponseEntity.created(location).body(clinicWebMapper.toRegisterClinicResponse(registerClinicResult));
    }

}
