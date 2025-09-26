package com.gacha.wiki.controller;

import com.gacha.wiki.entity.User;
import com.gacha.wiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }


    @PostMapping("")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("")
    public User update(@RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping("/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        return userService.save(user, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
