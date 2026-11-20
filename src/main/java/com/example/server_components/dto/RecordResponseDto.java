package com.example.server_components.dto;

import com.example.server_components.model.ExpenseRecord;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record RecordResponseDto(
        Long id,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("category_id") Long categoryId,
        @JsonProperty("created_at") String createdAt,
        BigDecimal amount
) {
    public static RecordResponseDto fromEntity(ExpenseRecord record) {
        return new RecordResponseDto(
                record.getId(),
                record.getUser().getId(),
                record.getCategory().getId(),
                record.getCreatedAt(),
                record.getAmount()
        );
    }
}