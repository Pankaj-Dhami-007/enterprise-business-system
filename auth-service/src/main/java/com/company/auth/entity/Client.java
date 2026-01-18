package com.company.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false, length = 150)
    private String name;

    @Column(name = "client_secret", nullable = false, unique = true, length = 50)
    private String clientSecret;

    @Column(name = "client_desc", length = 250)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
