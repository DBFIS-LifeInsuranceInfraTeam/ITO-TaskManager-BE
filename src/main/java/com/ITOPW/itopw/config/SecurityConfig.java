package com.ITOPW.itopw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
//                .authorizeHttpRequests(authorize -> authorize  // authorizeRequests() 대신 authorizeHttpRequests() 사용
//                        .requestMatchers("/api/**").permitAll()  // API 경로에 대한 인증 허용
//                        .requestMatchers("/images/**").permitAll()  // 이미지 경로에 대한 인증 허용
//                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
//                );
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
