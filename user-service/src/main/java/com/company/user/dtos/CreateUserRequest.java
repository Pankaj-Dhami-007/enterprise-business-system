package com.company.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest {

    private String username;
    private String email;
    private String fullName;
    private String mobile;
    private Long roleId;   // ADMIN / STAFF / MANAGER
}
