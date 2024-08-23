package com.patika.bloghubservice.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotNull(message = "Current password cannot be null")
        @NotBlank(message = "Current password cannot be blank")
        String currentPassword,
        @NotNull(message = "New password cannot be null")
        @NotBlank(message = "New password cannot be blank")
        @Size(min = 6, max = 12, message = "New password must be between 6 and 12 characters long")
        String newPassword
) {
}
