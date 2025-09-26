package com.gacha.wiki.service;

import com.gacha.wiki.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();


    User save(User user);

    User save(User user, Long id);

    void delete(Long id);

    Optional<User> findById(Long id);
}
