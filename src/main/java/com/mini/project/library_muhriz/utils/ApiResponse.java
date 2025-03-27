package com.mini.project.library_muhriz.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // ✅ Tambahkan NoArgsConstructor agar bisa digunakan oleh JSON parser
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    // ✅ Sukses dengan data (default message "Success")
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", data);
    }

    // ✅ Sukses dengan custom message
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }

    // ✅ Error dengan kode dan pesan saja
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    // ✅ Error dengan default status BAD_REQUEST (400)
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    // ✅ Error dengan kode, pesan, dan data tambahan
    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
