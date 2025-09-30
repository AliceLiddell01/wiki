package com.gacha.wiki.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gacha.wiki.entity.User;
import com.gacha.wiki.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id: " + id + " не найден"));
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id: " + id + " не найден"));

        existing.setEmail(user.getEmail());
        existing.setUsername(user.getUsername());
        existing.setRole(user.getRole());
        existing.setStatus(user.getStatus());
        existing.setLastLoginAt(user.getLastLoginAt());

        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public User partialUpdate(Long id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id: " + id + " не найден"));

        if (updates.containsKey("id") || updates.containsKey("passwordHash")) {
            updates.remove("id");
            updates.remove("passwordHash");
        }

        try {
            objectMapper.updateValue(user, updates);
        } catch (JsonMappingException e) {
            throw new IllegalArgumentException(e);
        }
        return userRepository.save(user);
    }
}
