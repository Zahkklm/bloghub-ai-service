package com.patika.bloghubservice.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message("SUCCESS!")
                .data(data)
                .success(true)
                .build();
    }

    public static <T> GenericResponse<T> success(T data, String message) {
        return GenericResponse.<T>builder()
                .message(message)
                .data(data)
                .success(true)
                .build();
    }

    public static <T> GenericResponse<T> error() {
        return GenericResponse.<T>builder()
                .message("ERROR!")
                .success(false)
                .build();
    }

    public static <T> GenericResponse<T> error(String message) {
        return GenericResponse.<T>builder()
                .message(message)
                .success(false)
                .build();
    }

    public static <T> GenericResponse<T> error(T data, String message) {
        return GenericResponse.<T>builder()
                .data(data)
                .message(message)
                .success(false)
                .build();
    }
}
