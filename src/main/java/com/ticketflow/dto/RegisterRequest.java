package com.ticketflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank(message = "İsim boş olamaz") String username,
                              @NotBlank(message = "Email boş olamaz") @Email(message = "Geçersiz email formatı") String email,
                              @NotBlank(message = "Şifre boş olamaz") @Size(min = 6, message = "Şifre en az 6 karakterli olmalı") String password) {
}
