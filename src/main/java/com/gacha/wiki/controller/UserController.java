package com.gacha.wiki.controller;

import com.gacha.wiki.entity.User;
import com.gacha.wiki.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
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
    public ResponseEntity<User> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(userService.findById(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("")
    public ResponseEntity<User> save(@Valid @RequestBody User user) {

        User created = userService.save(user);
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(userService.partialUpdate(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
