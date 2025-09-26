package com.gacha.wiki.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wiki_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    @NotBlank(message = "Поле электронной почты не может быть пустым")
    private String email;

    @Column(name = "password_hash")
    @NotBlank(message = "Пол с хеш паролем не может быть пустым")
    private String passwordHash;

    @Column(name = "created_at")
    @NotBlank(message = "Поле даты создания не может быть пустой")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "last_login_at")
    private Date lastLoginAt;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
