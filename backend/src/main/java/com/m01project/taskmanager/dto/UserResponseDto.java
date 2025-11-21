package com.m01project.taskmanager.dto;



public record UserResponseDto(Long id, String email) {
    // Java Record (compact immutable DTO). If you prefer class + getters, replace accordingly.
}
