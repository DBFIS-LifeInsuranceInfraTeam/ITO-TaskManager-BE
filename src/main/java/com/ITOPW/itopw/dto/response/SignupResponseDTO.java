package com.ITOPW.itopw.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDTO {

    private String id;  // 사용자 ID
    private String name;  // 사용자 이름

    // 생성자
    public SignupResponseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
