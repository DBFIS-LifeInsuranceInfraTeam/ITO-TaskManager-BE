package com.ITOPW.itopw.service;

import com.ITOPW.itopw.dto.request.UserUpdateRequest;
import com.ITOPW.itopw.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //회원가입
    User registerUser(User user);

    //id로 사용자 조회
    Optional<User> getUserByUserId(String userId);

    //email로 사용자 조회
    Optional<User> getUserByEmail(String email);

    //전체 사용자 조회
    List<User> getAllUsers();

    //사용자 삭제
    void deleteUser(String id);

    // 로그인 인증
    Optional<User> authenticateUser(String userId, String password);

    //List<User> getUsersByProjectId(String projectId);
    List<User> getUsersByProjectIdList(List<String> projectIds);

    List<User> getUsersByProjectIdListAndUnit(List<String> projectIds, String unit);

    User updateUser(String userId, UserUpdateRequest request);

    //프로필이미지 업로드
    void updateProfileImage(String userId, String profileImagePath);
}