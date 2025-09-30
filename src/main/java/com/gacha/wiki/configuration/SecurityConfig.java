package com.gacha.wiki.configuration;

import com.gacha.wiki.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Отключение защиты от CSRF так как REST
                .csrf(AbstractHttpConfigurer::disable)

                // Полностью отключаем сессии, так как REST
                .sessionManagement(sm -> sm

                        // .sessionFixation().migrateSession() (защита от session fixation attack)
                        // Создаёт новую сессию, скопировав атрибуты

                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                        // .maximumSessions(1) - ограничение числа сессий
                        // .maxSessionsPreventsLogin(false) - что делать с сессии при новом входе. По умолчанию убивается
                        // .expiredUrl("/login?expired=true") - редирект, если сессия устарела
                )

                //Формирование правил доступа
                .authorizeHttpRequests(auth-> auth

                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )


                //Html-форма для логина
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home", true)
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )

                //Форма выхода и очистки сессии
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                )


                //Базовая аутентификация
                .httpBasic(basicAuth -> {})

                .build();
    }

    //Хэширование пароля
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
