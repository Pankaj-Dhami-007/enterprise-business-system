package com.company.auth.repository;

import com.company.auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientSecretAndIsActiveTrue(String clientSecret);

    Optional<Client> findByName(String acmeCorp);
}