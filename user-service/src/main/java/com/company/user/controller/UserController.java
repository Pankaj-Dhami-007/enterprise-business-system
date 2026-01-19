package com.company.user.controller;

import com.company.user.dtos.CreateUserRequest;
import com.company.user.dtos.UserResponse;
import com.company.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestHeader("X-CLIENT-ID") Long clientId,
            @RequestBody CreateUserRequest request
    ) {
        return new ResponseEntity<>(userService.createUser(clientId, request), HttpStatus.CREATED);
    }
}
