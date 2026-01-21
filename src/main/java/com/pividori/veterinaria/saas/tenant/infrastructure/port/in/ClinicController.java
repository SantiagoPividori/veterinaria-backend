package com.pividori.veterinaria.saas.tenant.infrastructure.port.in;

import com.pividori.veterinaria.saas.tenant.application.port.in.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.infrastructure.port.in.mapper.ClinicWebMapper;
import com.pividori.veterinaria.saas.tenant.infrastructure.port.in.dto.RegisterClinicRequest;
import com.pividori.veterinaria.saas.tenant.infrastructure.port.in.dto.RegisterClinicResponse;
import com.pividori.veterinaria.saas.tenant.application.port.in.RegisterClinicUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("saas/clincs")
@RequiredArgsConstructor
public class ClinicController {

    private final RegisterClinicUseCase registerClinicUseCase;

    @PostMapping("/register")
    public RegisterClinicResponse registerClinic(@Valid @RequestBody RegisterClinicRequest registerClinicRequest) {
        RegisterClinicResult registerClinicResult =
                registerClinicUseCase.register(ClinicWebMapper.toRegisterClinicCommand(registerClinicRequest));
        return ClinicWebMapper.toRegisterClinicResponse(registerClinicResult);
    }

}
