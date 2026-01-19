package com.company.user.service;

import com.company.user.dtos.CreateUserRequest;
import com.company.user.dtos.UserResponse;

public interface UserService {
    UserResponse createUser(Long clientId ,CreateUserRequest userRequest);
}
