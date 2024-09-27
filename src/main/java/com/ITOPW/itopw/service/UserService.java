package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //회원가입
    User registerUser(User user);

    //id로 사용자 조회
    Optional<User> getUserById(String id);

    //Optional<User> getUserByEmail(String email);

    //전체 사용자 조회
    List<User> getAllUsers();

    //사용자 삭제
    void deleteUser(String id);

    // 로그인 인증
    boolean authenticateUser(String id, String password);
}