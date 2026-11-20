package com.example.server_components.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    @NotBlank(message = "Field 'name' must not be blank")
    @Size(min = 2, max = 100, message = "Field 'name' must be between 2 and 100 characters")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}