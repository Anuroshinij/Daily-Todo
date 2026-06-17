package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend.security.jwt.JwtAutheticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAutheticationFilter jwtAutheticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                                                .sessionCreationPolicy(
                                                     SessionCreationPolicy.STATELESS
                                                ))
                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAutheticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
