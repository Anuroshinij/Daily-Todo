package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "UserName is required")
    @Size(min = 3, max = 30,
          message = "Username must be between 3 and 30 characters")
    @Pattern(
        regexp = "^[a-zA-Z0-9_]+$",
        message = "Username can contain only letters, numbers and underscore"
    )
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20,
          message = "Password must be between 8 and 20 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter and one number"
    )
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

}
