package com.example.server_components.dto;

import com.example.server_components.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryResponseDto(
        Long id,
        String name,
        @JsonProperty("user_id") Long userId
) {
    public static CategoryResponseDto fromEntity(Category category) {
        Long ownerId = category.getUser() != null ? category.getUser().getId() : null;
        return new CategoryResponseDto(category.getId(), category.getName(), ownerId);
    }
}