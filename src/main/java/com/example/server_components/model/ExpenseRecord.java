package com.example.server_components.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "records")
public class ExpenseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at", nullable = false, length = 64)
    private String createdAt;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    public ExpenseRecord() {}

    public ExpenseRecord(User user, Category category, String createdAt, BigDecimal amount) {
        this.user = user;
        this.category = category;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}