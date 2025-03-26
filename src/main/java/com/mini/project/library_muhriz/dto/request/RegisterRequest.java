package com.mini.project.library_muhriz.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email harus menggunakan domain yang valid (contoh: example@gmail.com, user@company.co.id)"
    )
    private String email;

    @Size(min = 8, message = "Password harus minimal 8 karakter")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z0-9])[A-Za-z0-9]{8,}$",
            message = "Password harus terdiri dari minimal 8 karakter alphanumeric, mengandung minimal 1 huruf kapital, dan tidak boleh mengandung karakter spesial"
    )
    private String password;
}
