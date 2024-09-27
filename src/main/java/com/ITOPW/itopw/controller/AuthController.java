package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.request.LoginRequestDTO;
import com.ITOPW.itopw.dto.request.SignupRequestDTO;
import com.ITOPW.itopw.dto.response.SignupResponseDTO;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")  // 기본 URL 경로
public class AuthController {

    @Autowired
    private UserService userService;

    // 회원가입 요청 처리
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO signupRequest) {
        User user = new User();
        user.setId(signupRequest.getId());
        user.setName(signupRequest.getName());
        user.setPassword(signupRequest.getPassword());
        user.setUnit(signupRequest.getUnit());
        user.setTeam(signupRequest.getTeam());
        user.setEmail(signupRequest.getEmail());
        user.setPhone_number(signupRequest.getPhone_number());
        user.setPhoto(signupRequest.getPhoto());
        user.setPosition(signupRequest.getPosition());


        userService.registerUser(user);

        SignupResponseDTO response = new SignupResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setMessage("회원가입 성공");

        return new ResponseEntity<>(response, HttpStatus.CREATED);  // 201 Created 응답

    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        // 로그인 요청 처리
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getId(), loginRequest.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: ID 또는 비밀번호가 잘못되었습니다.");
        }
    }
}