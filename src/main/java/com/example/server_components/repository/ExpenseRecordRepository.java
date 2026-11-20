package com.example.server_components.repository;

import com.example.server_components.model.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {

    @Query("""
           SELECT r FROM ExpenseRecord r
           WHERE (:userId IS NULL OR r.user.id = :userId)
             AND (:categoryId IS NULL OR r.category.id = :categoryId)
           """)
    List<ExpenseRecord> filterRecords(@Param("userId") Long userId,
                                      @Param("categoryId") Long categoryId);
}