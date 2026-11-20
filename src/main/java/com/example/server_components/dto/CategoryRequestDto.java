package com.example.server_components.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CategoryRequestDto {

    @NotBlank(message = "Field 'name' must not be blank")
    @Size(min = 2, max = 100, message = "Field 'name' must be between 2 and 100 characters")
    private String name;

    @JsonProperty("user_id")
    @Positive(message = "Field 'user_id' must be a positive number")
    private Long userId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}