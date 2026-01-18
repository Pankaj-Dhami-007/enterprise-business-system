package com.company.auth.repository;

import com.company.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUsernameAndIsActiveTrue(String username);
    Optional<UserAuth> findByEmailAndIsActiveTrue(String email);

    Optional<Object> findByUsername(String admin);
}