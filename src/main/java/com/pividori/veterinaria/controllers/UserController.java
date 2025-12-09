package com.pividori.veterinaria.controllers;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest request) {
        UserResponse user = userService.register(request);
        return ResponseEntity.created(URI.create("/user/" + user.id())).body(user);
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

}
