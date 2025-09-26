package com.gacha.wiki.repository;

import com.gacha.wiki.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
