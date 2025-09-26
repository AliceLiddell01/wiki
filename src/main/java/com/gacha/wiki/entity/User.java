package com.gacha.wiki.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "wiki_users")
@ToString(exclude = "passwordHash")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле ника не может быть пустым")
    @Size(min = 3, max = 32, message = "Имя пользователя от 3 до 32 символов")
    @Column(unique = true, nullable = false, length = 32)
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$",
            message = "Разрешены только буквы, цифры и символы: '_', '.', '-', пробелы недопустимы"
    )
    private String username;

    @Email(message = "Неккоректный формат электронныой почты")
    @NotBlank(message = "Поле электронной почты не может быть пустым")
    @Size(max = 255, message = "Длина почты должны быть не больше 255 символов")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    @Size(max = 255, message = "Слишком длинный хеш пароля")
    @NotBlank(message = "Пол с хеш паролем не может быть пустым")
    @JsonIgnore
    private String passwordHash;

    @CreatedDate
    @PastOrPresent(message = "Неужели Вы Джон Титор? Если нет - дата создания не может быть из будущего")
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "last_login_at")
    private OffsetDateTime lastLoginAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private UserStatus status = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
}
