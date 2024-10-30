package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);

    // ID로 사용자 조회
    Optional<User> findByUserId(String userId);

    // 프로젝트 ID로 사용자 목록 조회
    List<User> findByProjectId(String projectId);
}