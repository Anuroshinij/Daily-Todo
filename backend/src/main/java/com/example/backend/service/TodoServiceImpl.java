package com.example.backend.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.TodoRequestDto;
import com.example.backend.dto.TodoResponseDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.mapper.TodoMapper;
import com.example.backend.model.Todo;
import com.example.backend.model.User;
import com.example.backend.repository.TodoRepository;
import com.example.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

        return userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<TodoResponseDto> getAllTodos() {

        return repo.findByUser(getCurrentUser())
                .stream()
                .map(TodoMapper::toResponseDto)
                .toList();
    }

    @Override
    public TodoResponseDto getTodoById(Long id) {

        Todo todo = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found with id: " + id));

        User currentUser = getCurrentUser();

        if(!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Todo not found");
        }

        return TodoMapper.toResponseDto(todo);
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {

        Todo todo = TodoMapper.toEntity(dto);

        todo.setUser(getCurrentUser());

        Todo savedTodo = repo.save(todo);

        return TodoMapper.toResponseDto(savedTodo);
    }

    @Override
    public TodoResponseDto updateTodo(Long id,
                                      TodoRequestDto dto) {

        Todo existingTodo = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found with id: " + id));

        User currentUser = getCurrentUser();

        if(!existingTodo.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Todo not found");
        }

        existingTodo.setTitle(dto.getTitle());
        existingTodo.setDescription(dto.getDescription());

        Todo updatedTodo = repo.save(existingTodo);

        return TodoMapper.toResponseDto(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id) {

        Todo todo = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found with id: " + id));

        User currentUser = getCurrentUser();

        if(!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Todo not found");
        }

        repo.delete(todo);
    }
}