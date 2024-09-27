package com.ITOPW.itopw.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDTO {

    private String id;  // 사용자 ID
    private String name;  // 사용자 이름
    private String message;  // 응답 메시지 (예: "회원가입 성공")
}
