package com.ITOPW.itopw.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PROJECT") // 테이블 이름을 "PROJECT"로 설정
public class Project {

    @Id // 기본 키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정 (데이터베이스에 따라 설정)
    @Column(name = "project_id", nullable = false) // not null 제약 조건
    private Integer projectId;

    @Column(name = "name", nullable = false) // not null 제약 조건
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "smtp_url")
    private String smtpUrl;

    @Column(name = "smtp_id")
    private String smtpId;

    @Column(name = "smtp_pw")
    private String smtpPw;

    // 기본 생성자
    public Project() {
    }

    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmtpUrl() {
        return smtpUrl;
    }

    public void setSmtpUrl(String smtpUrl) {
        this.smtpUrl = smtpUrl;
    }

    public String getSmtpId() {
        return smtpId;
    }

    public void setSmtpId(String smtpId) {
        this.smtpId = smtpId;
    }

    public String getSmtpPw() {
        return smtpPw;
    }

    public void setSmtpPw(String smtpPw) {
        this.smtpPw = smtpPw;
    }
}
