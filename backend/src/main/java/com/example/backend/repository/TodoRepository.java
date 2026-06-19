package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.Todo;
import com.example.backend.model.User;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);

}
