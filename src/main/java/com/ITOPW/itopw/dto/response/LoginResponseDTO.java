package com.ITOPW.itopw.dto.response;

import com.ITOPW.itopw.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;      // JWT 토큰
    private int expiresIn;     // 토큰 유효 기간 (초 단위)
    private UserInfoResponseDto userInfo;         // 로그인한 사용자 정보


    // 파라미터가 있는 생성자
    public LoginResponseDTO(String token, int expiresIn, UserInfoResponseDto userInfo) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.userInfo = userInfo;
    }
}
