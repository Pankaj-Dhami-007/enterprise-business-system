package com.company.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users_auth")
public class UserAuth extends BaseEntity{

    @Id
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
