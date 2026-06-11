package com.example.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.AuthResponseDto;
import com.example.backend.dto.LoginRequestDto;
import com.example.backend.dto.RegisterRequestDto;
import com.example.backend.exception.InvalidCredentialsException;
import com.example.backend.exception.UserNameAlreadyExistsException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.Role;
import com.example.backend.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto register(RegisterRequestDto dto) {

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password doesn't match");
        }

        if (userRepository.findByUserName(
                dto.getUserName()).isPresent()) {

            throw new UserNameAlreadyExistsException("UserName already exists");
        }

        User user = User.builder()
                .userName(dto.getUserName())
                .password(
                        passwordEncoder.encode(
                                dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUserName());

        return AuthResponseDto.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponseDto login(
            LoginRequestDto dto) {

        User user = userRepository
                .findByUserName(dto.getUserName())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Invalid username or password"));

        boolean isPasswordValid = passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword());

        if (!isPasswordValid) {

            throw new InvalidCredentialsException(
                    "Invalid username or password");
        }

        String token = jwtService.generateToken(
                user.getUserName());

        return AuthResponseDto.builder()
                .token(token)
                .build();
    }

}
