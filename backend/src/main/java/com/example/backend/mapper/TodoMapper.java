package com.example.backend.mapper;

import com.example.backend.dto.TodoRequestDto;
import com.example.backend.dto.TodoResponseDto;
import com.example.backend.model.Todo;

public class TodoMapper {

    private TodoMapper() {
    }

    public static TodoResponseDto toResponseDto(Todo todo) {

        return TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .build();
    }

    public static Todo toEntity(TodoRequestDto dto) {

        return Todo.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(false)
                .build();
    }
}