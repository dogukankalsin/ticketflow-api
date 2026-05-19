package com.ticketflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank(message = "Email boş olamaz") @Email(message = "Geçersiz email formatı")String email,
                           @NotBlank(message = "Şifre boş olamaz") @Size(min = 6, message = "Şifre en az 6 karakter olmalı") String password) {
}
