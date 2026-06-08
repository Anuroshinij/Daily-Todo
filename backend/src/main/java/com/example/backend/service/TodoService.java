package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.TodoRequestDto;
import com.example.backend.dto.TodoResponseDto;

public interface TodoService {
    List<TodoResponseDto> getAllTodos();
    TodoResponseDto getTodoById(Long id);
    TodoResponseDto createTodo(TodoRequestDto todo);
    TodoResponseDto updateTodo(Long id, TodoRequestDto todo);
    void deleteTodo(Long id);
}
