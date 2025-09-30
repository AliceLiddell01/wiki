package com.gacha.wiki.service;

import com.gacha.wiki.entity.SecurityUser;
import com.gacha.wiki.entity.User;
import com.gacha.wiki.entity.UserStatus;
import com.gacha.wiki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с почтой: " + email + " не найден"));

        if (user.getStatus() == UserStatus.BANNED) {
            throw new UsernameNotFoundException("Пользователь забанен");
        }

        return new SecurityUser(user);
    }
}
