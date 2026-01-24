package com.pividori.veterinaria.saas.iam.infrastructure.port.in;

import com.pividori.veterinaria.saas.iam.application.service.AuthServiceImpl;
import com.pividori.veterinaria.saas.iam.infrastructure.port.in.dtos.AuthResponse;
import com.pividori.veterinaria.saas.iam.infrastructure.port.in.dtos.LoginRequest;
import com.pividori.veterinaria.saas.iam.infrastructure.port.in.dtos.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pividori.veterinaria.shared.api.ApiPaths.API_V1;

@RestController
@RequestMapping(API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

}
