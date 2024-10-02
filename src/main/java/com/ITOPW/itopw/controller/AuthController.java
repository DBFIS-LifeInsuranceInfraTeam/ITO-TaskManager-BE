package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.request.LoginRequestDTO;
import com.ITOPW.itopw.dto.request.SignupRequestDTO;
import com.ITOPW.itopw.dto.response.BaseResponseDTO;
import com.ITOPW.itopw.dto.response.LoginResponseDTO;
import com.ITOPW.itopw.dto.response.SignupResponseDTO;
import com.ITOPW.itopw.dto.response.UserInfoResponseDto;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.service.UserService;
import com.ITOPW.itopw.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")  // 기본 URL 경로
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; // JwtUtil 주입

    // 회원가입 요청 처리
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDTO<SignupResponseDTO>> signup(@RequestBody SignupRequestDTO signupRequest) {
        // 이미 등록된 사용자 확인
        Optional<User> existingUserById = userService.getUserById(signupRequest.getId());
        Optional<User> existingUserByEmail = userService.getUserByEmail(signupRequest.getEmail());

        if (existingUserById.isPresent() || existingUserByEmail.isPresent()) {
            // 이미 가입된 사용자에 대한 응답
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseDTO<>(409, "Conflict", "이미 가입된 사용자입니다."));
        }

        User user = new User();
        user.setId(signupRequest.getId());
        user.setName(signupRequest.getName());
        user.setPassword(signupRequest.getPassword());
        user.setUnit(signupRequest.getUnit());
        user.setTeam(signupRequest.getTeam());
        user.setEmail(signupRequest.getEmail());
        user.setPhone_number(signupRequest.getPhone_number());
        // photo가 null이면 기본 이미지 URL로 설정
        // 프로필 이미지 목록
        String[] profileImages = {
                "images/profile01.png",
                "images/profile02.png",
                "images/profile03.png",
                "images/profile04.png",
                "images/profile05.png",
                "images/profile06.png"
        };

        // 랜덤 이미지 선택
        Random random = new Random();
        String selectedImage = profileImages[random.nextInt(profileImages.length)];
        user.setPhoto(signupRequest.getPhoto() != null ? signupRequest.getPhoto() : selectedImage);
        user.setPosition(signupRequest.getPosition());

        userService.registerUser(user);

        SignupResponseDTO signupResponse = new SignupResponseDTO(user.getId(), user.getName());
        BaseResponseDTO<SignupResponseDTO> response = new BaseResponseDTO<>(201, "Created", "회원가입 성공", signupResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO<LoginResponseDTO>> login(@RequestBody LoginRequestDTO loginRequest) {
        // 사용자 인증 시도
        Optional<User> userOptional = userService.authenticateUser(loginRequest.getId(), loginRequest.getPassword());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = jwtUtil.generateToken(user.getId()); // JWT 생성
            UserInfoResponseDto userInfo = new UserInfoResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone_number(),
                    user.getPhoto(),
                    user.getPosition()
            );
            LoginResponseDTO loginResponse = new LoginResponseDTO(token,3600, userInfo);
            BaseResponseDTO<LoginResponseDTO> response = new BaseResponseDTO<>(200, "OK", "로그인 성공", loginResponse);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            BaseResponseDTO<LoginResponseDTO> response = new BaseResponseDTO<>(401, "Unauthorized", "ID 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}