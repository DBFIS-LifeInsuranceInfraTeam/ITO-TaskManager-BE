package com.ITOPW.itopw.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "project")
public class Project {

    @Id // 기본 키로 설정
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "smtp_url")
    private String smtpUrl;

    @Column(name = "smtp_id")
    private String smtpId;

    @Column(name = "smtp_pw")
    private String smtpPw;
}
