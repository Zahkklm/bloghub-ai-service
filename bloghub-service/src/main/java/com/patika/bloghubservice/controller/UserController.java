package com.patika.bloghubservice.controller;

import com.patika.bloghubservice.dto.request.ChangePasswordRequest;
import com.patika.bloghubservice.dto.request.UserSaveRequest;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.dto.response.UserResponse;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.model.enums.UserType;
import com.patika.bloghubservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public GenericResponse<UserResponse> createUser(@RequestBody UserSaveRequest request) {
        return GenericResponse.success(userService.saveUser(request));
    }

    @GetMapping("/{email}")
    public GenericResponse<UserResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(userService.getUserByEmail(email));
    }

    @GetMapping
    public GenericResponse<List<UserResponse>> getAllUsers() {
        return GenericResponse.success(userService.getAllUsers());
    }

    @PutMapping("/{email}")
    public ResponseEntity<GenericResponse<StatusType>> changeStatus(@PathVariable String email, @RequestParam("statusType") StatusType statusType) {
        return ResponseEntity.ok(userService.changeStatus(email, statusType));
    }

    @PatchMapping("/{email}")
    public ResponseEntity<GenericResponse<UserType>> changeUserType(@PathVariable String email, @RequestParam("userType") UserType userType) {
        return ResponseEntity.ok(userService.changeUserType(email, userType));
    }


    @PatchMapping("/{email}/password")
    public GenericResponse<UserResponse> changePassword(
            @PathVariable String email,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        return GenericResponse.success(userService.changePassword(email, changePasswordRequest));
    }

    //ödev şifre değiştirenn endpoint
}
