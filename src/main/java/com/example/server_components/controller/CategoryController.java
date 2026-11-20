package com.example.server_components.controller;

import com.example.server_components.dto.CategoryRequestDto;
import com.example.server_components.dto.CategoryResponseDto;
import com.example.server_components.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final ExpenseService expenseService;

    public CategoryController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDto>> getCategories(
            @RequestParam(value = "user_id", required = false) Long userId) {
        return ResponseEntity.ok(expenseService.getCategories(userId));
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.createCategory(request));
    }

    @DeleteMapping("/category/{category_id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("category_id") Long categoryId,
            @RequestParam(value = "user_id", required = false) Long userId) {
        expenseService.deleteCategory(categoryId, userId);
        return ResponseEntity.noContent().build();
    }
}