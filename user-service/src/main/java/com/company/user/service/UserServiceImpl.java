package com.company.user.service;

import com.company.user.dtos.CreateUserRequest;
import com.company.user.dtos.UserResponse;
import com.company.user.entity.RoleEntity;
import com.company.user.entity.User;
import com.company.user.repository.RoleEntityRepository;
import com.company.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final RoleEntityRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(Long clientId, CreateUserRequest request) {

        log.info("Creating user username={} for clientId={}", request.getUsername(), clientId);

        RoleEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setClientId(clientId);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setMobile(request.getMobile());
        user.setRole(role);
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                role.getRoleName()
        );
    }
}
