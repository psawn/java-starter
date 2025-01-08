package com.example.demo.validation.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUser {
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;
}
