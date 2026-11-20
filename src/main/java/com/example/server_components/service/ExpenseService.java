package com.example.server_components.service;

import com.example.server_components.dto.*;
import com.example.server_components.exception.ApiException;
import com.example.server_components.model.Category;
import com.example.server_components.model.ExpenseRecord;
import com.example.server_components.model.User;
import com.example.server_components.repository.CategoryRepository;
import com.example.server_components.repository.ExpenseRecordRepository;
import com.example.server_components.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ExpenseService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRecordRepository recordRepository;

    public ExpenseService(UserRepository userRepository,
                          CategoryRepository categoryRepository,
                          ExpenseRecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.recordRepository = recordRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        return UserResponseDto.fromEntity(findUserEntity(id));
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        User saved = userRepository.save(new User(request.getName().trim()));
        return UserResponseDto.fromEntity(saved);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(findUserEntity(id));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategories(Long userId) {
        List<Category> categories;
        if (userId == null) {
            categories = categoryRepository.findByUserIsNull();
        } else {
            findUserEntity(userId);
            categories = categoryRepository.findVisibleForUser(userId);
        }
        return categories.stream()
                .map(CategoryResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        User owner = null;
        if (request.getUserId() != null) {
            owner = findUserEntity(request.getUserId());
        }
        Category saved = categoryRepository.save(new Category(request.getName().trim(), owner));
        return CategoryResponseDto.fromEntity(saved);
    }

    @Transactional
    public void deleteCategory(Long categoryId, Long userId) {
        Category category = findCategoryEntity(categoryId);
        if (category.getUser() != null) {
            if (userId == null || !category.getUser().getId().equals(userId)) {
                throw new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "category_access_denied",
                        "Personal category can only be deleted by its owner (provide matching 'user_id')"
                );
            }
        }
        categoryRepository.delete(category);
    }

    @Transactional
    public RecordResponseDto createRecord(RecordRequestDto request) {
        User user = findUserEntity(request.getUserId());
        Category category = findCategoryEntity(request.getCategoryId());

        if (category.getUser() != null && !category.getUser().getId().equals(user.getId())) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "category_access_denied",
                    "Personal category belongs to another user and cannot be selected"
            );
        }

        String createdAt = (request.getCreatedAt() != null && !request.getCreatedAt().isBlank())
                ? request.getCreatedAt()
                : Instant.now().toString();

        ExpenseRecord saved = recordRepository.save(
                new ExpenseRecord(user, category, createdAt, request.getAmount())
        );
        return RecordResponseDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public RecordResponseDto getRecordById(Long id) {
        ExpenseRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not_found",
                        "Record with id " + id + " not found"));
        return RecordResponseDto.fromEntity(record);
    }

    @Transactional
    public void deleteRecord(Long id) {
        ExpenseRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not_found",
                        "Record with id " + id + " not found"));
        recordRepository.delete(record);
    }

    @Transactional(readOnly = true)
    public List<RecordResponseDto> filterRecords(Long userId, Long categoryId) {
        if (userId == null && categoryId == null) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "invalid_request",
                    "At least one query parameter ('user_id' or 'category_id') is required for filtration"
            );
        }
        return recordRepository.filterRecords(userId, categoryId).stream()
                .map(RecordResponseDto::fromEntity)
                .toList();
    }

    private User findUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not_found",
                        "User with id " + id + " not found"));
    }

    private Category findCategoryEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not_found",
                        "Category with id " + id + " not found"));
    }
}