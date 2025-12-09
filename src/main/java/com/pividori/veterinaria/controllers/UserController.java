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

//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId) {
//        //return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
//        return ResponseEntity.ok(userService.finById(userId));
//    }

    //ToDo: #Agregar el PreAuthorize con ADMIN y devolver un Plegable(investigar).
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
