package com.pividori.veterinaria.onboarding.infrastructure.port.in;

import com.pividori.veterinaria.onboarding.application.port.in.RegisterResult;
import com.pividori.veterinaria.identity.application.port.in.RegisterUseCase;
import com.pividori.veterinaria.onboarding.infrastructure.dto.RegisterRequest;
import com.pividori.veterinaria.onboarding.infrastructure.dto.RegisterResponse;
import com.pividori.veterinaria.onboarding.infrastructure.mapper.OnboardingWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResult registerResult =
                registerUseCase.register(OnboardingWebMapper.toRegisterCommand(registerRequest));
        return ResponseEntity.ok(OnboardingWebMapper.toRegisterResponse(registerResult));
    }

}
