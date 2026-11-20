package com.example.server_components.controller;

import com.example.server_components.dto.UserRequestDto;
import com.example.server_components.dto.UserResponseDto;
import com.example.server_components.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final ExpenseService expenseService;

    public UserController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(expenseService.getAllUsers());
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(expenseService.getUserById(userId));
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.createUser(request));
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long userId) {
        expenseService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}