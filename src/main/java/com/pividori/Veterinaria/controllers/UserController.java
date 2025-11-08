package com.pividori.Veterinaria.controllers;

import com.pividori.Veterinaria.dto.CreateUserRequest;
import com.pividori.Veterinaria.dto.UserResponse;
import com.pividori.Veterinaria.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.created(URI.create("/user/" + user.id())).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId) {
        //return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
