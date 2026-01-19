package com.company.user.repository;

import com.company.user.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionEntityRepository extends JpaRepository<RolePermissionEntity, Long> {
}