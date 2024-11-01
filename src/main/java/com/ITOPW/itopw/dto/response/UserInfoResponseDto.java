package com.ITOPW.itopw.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {
    private String userId; // 사용자 ID
    private String name; // 이름
    private String email; // 이메일
    private String phoneNumber; // 전화번호
    private String photo; // 사진
    private String position; // 직급
    private String unit; //유닛
    private String projectId;

    // 생성자
    public UserInfoResponseDto(String userId, String name, String email, String phoneNumber, String photo, String position, String unit,String projectId) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.position = position;
        this.unit = unit;
        this.projectId = projectId;
    }
}