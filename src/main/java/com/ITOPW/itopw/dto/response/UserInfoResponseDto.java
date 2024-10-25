package com.ITOPW.itopw.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {
    private String id; // 사용자 ID
    private String name; // 이름
    private String email; // 이메일
    private String phone_number; // 전화번호
    private String photo; // 사진
    private String position; // 직급

    // 생성자
    public UserInfoResponseDto(String id, String name, String email, String phone_number, String photo, String position) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.photo = photo;
        this.position = position;
    }
}