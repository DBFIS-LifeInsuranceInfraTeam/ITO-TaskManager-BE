package com.ITOPW.itopw.entity;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"user\"")
public class  User {

    @Id
    @Column(name = "user_id", length = 50)
    private String userId;  // 사용자가 입력하는 ID

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "unit", length = 50)
    private String unit;

//    @Column(name = "project_id", length = 50)
//    private String projectId;

    @Type(ListArrayType.class)
    @Column(
            name = "project_id",
            columnDefinition = "text[]"
    )
    private List<String> projectId = new ArrayList<>();

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    // 이미지 경로를 저장할 경우 String 사용
    @Column(name = "photo")
    private String photo;

    @Column(name = "position", length = 50)
    private String position;

    @Column(name = "admin")
    private Boolean admin;

}
