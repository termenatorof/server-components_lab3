package com.example.server_components.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RecordRequestDto {

    @JsonProperty("user_id")
    @NotNull(message = "Field 'user_id' is required")
    @Positive(message = "Field 'user_id' must be a positive number")
    private Long userId;

    @JsonProperty("category_id")
    @NotNull(message = "Field 'category_id' is required")
    @Positive(message = "Field 'category_id' must be a positive number")
    private Long categoryId;

    @JsonProperty("created_at")
    private String createdAt;

    @NotNull(message = "Field 'amount' is required")
    @DecimalMin(value = "0.01", message = "Field 'amount' must be at least 0.01")
    private BigDecimal amount;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}