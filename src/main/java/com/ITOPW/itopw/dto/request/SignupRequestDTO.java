package com.ITOPW.itopw.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {

    private String id;  // 사용자 ID
    private String name;  // 사용자 이름
    private String password;  // 비밀번호
    private String unit;  // 소속 유닛
    private String team;  // 소속 팀
    private String email;  // 이메일
    private String phone_number;  // 전화번호
    private String photo;  // 사진 경로
    private String position;  // 직급
}

