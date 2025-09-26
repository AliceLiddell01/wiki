package com.gacha.wiki.service;

import com.gacha.wiki.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User save(User user);

    boolean delete(Long id);

    User findById(Long id);

    User update(Long id, User user);

    User partialUpdate(Long id, Map<String, Object> updates);
}
