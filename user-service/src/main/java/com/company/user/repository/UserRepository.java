package com.company.user.repository;

import com.company.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByClientIdAndUsername(Long clientId, String username);
  Optional<User> findByClientIdAndEmail(Long clientId, String email);
  List<User> findAllByClientId(Long clientId);
}