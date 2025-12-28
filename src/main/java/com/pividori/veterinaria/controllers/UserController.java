package com.pividori.veterinaria.controllers;

import com.pividori.veterinaria.dtos.UpdateUserRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.pividori.veterinaria.api.ApiPaths.API_V1;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1 + "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    //ToDo: #Agregar el PreAuthorize con ADMIN.
    @GetMapping("/byusername/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    //ToDo: #Agregar el PreAuthorize con ADMIN y devolver un Plegable(investigar).
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    //ToDo: #Agregar el PreAuthorize con ADMIN.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //ToDo: #Modificarlo para que solo pueda modificar los datos el mismo usuario, no cualquiera
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }
}
