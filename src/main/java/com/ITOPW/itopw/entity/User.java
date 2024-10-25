package com.ITOPW.itopw.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", length = 50)
    private String id;  // 사용자가 입력하는 ID

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "team", length = 50)
    private String team;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phone_number;

    // 이미지 경로를 저장할 경우 String 사용
    @Column(name = "photo")
    private String photo;

    @Column(name = "position", length = 50)
    private String position;

}
