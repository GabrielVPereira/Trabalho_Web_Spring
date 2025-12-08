package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.Data; // Voltando a usar Lombok para limpar o código visualmente

@Data // Gera Getters, Setters, ToString, etc.
@Entity
@Table(name = "users")
public class User { // SEM implements UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role; 
}