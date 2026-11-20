package com.example.server_components.controller;

import com.example.server_components.dto.RecordRequestDto;
import com.example.server_components.dto.RecordResponseDto;
import com.example.server_components.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final ExpenseService expenseService;

    public RecordController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/record")
    public ResponseEntity<RecordResponseDto> createRecord(@Valid @RequestBody RecordRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.createRecord(request));
    }

    @GetMapping("/record/{record_id}")
    public ResponseEntity<RecordResponseDto> getRecordById(@PathVariable("record_id") Long recordId) {
        return ResponseEntity.ok(expenseService.getRecordById(recordId));
    }

    @DeleteMapping("/record/{record_id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable("record_id") Long recordId) {
        expenseService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/record")
    public ResponseEntity<List<RecordResponseDto>> filterRecords(
            @RequestParam(value = "user_id", required = false) Long userId,
            @RequestParam(value = "category_id", required = false) Long categoryId) {
        return ResponseEntity.ok(expenseService.filterRecords(userId, categoryId));
    }
}