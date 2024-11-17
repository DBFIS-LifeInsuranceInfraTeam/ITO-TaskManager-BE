package com.ITOPW.itopw.entity;

import com.ITOPW.itopw.dto.TaskRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "task")  // DB 테이블 이름과 매핑
public class Task {

    @Id
    @Column(name = "task_id", nullable = false) // PK, NOT NULL
    private String taskId;

    @Column(name = "project_id", nullable = false) // NOT NULL
    private String projectId;

    @Column(name = "task_name", nullable = false) // NOT NULL
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "assignee_id", nullable = false) // NOT NULL
    private String assigneeId;

    @Column(name = "created_date", nullable = false) // NOT NULL
    private LocalDate createdDate;

    @Column(name = "start_date", nullable = false) // NOT NULL
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "frequency_id") // FK, nullable
    private Integer frequencyId;

    @Column(name = "comment_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0") // NOT NULL, 기본값 0
    private Integer commentCount = 0;  // 기본값 0으로 초기화

    @Column(name = "status", nullable = false) // NOT NULL
    private Integer status;

    @Column(name = "ito_process_id", nullable = false) // NOT NULL
    private String itoProcessId;

    @Column(name = "assignee_confirmation", nullable = false) // NOT NULL
    private String assigneeConfirmation;

    @Column(name = "created_by", nullable = false) // NOT NULL
    private String createdBy;

    @ManyToMany
    @JoinTable(
            name = "task_assignee",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignees = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore // 전체 조회 시 이 필드를 응답에 포함하지 않음
    private List<Comment> comments;


    public Task(TaskRequest taskRequest) {
        this.projectId = taskRequest.getProjectId();
        this.taskName = taskRequest.getTaskName();
        this.description = taskRequest.getDescription();
        this.assigneeId = taskRequest.getAssigneeId();
        this.createdDate = taskRequest.getCreatedDate();
        this.startDate = taskRequest.getStartDate();
        this.dueDate = taskRequest.getDueDate();
        this.frequencyId = taskRequest.getFrequencyId();
        this.status = taskRequest.getStatus();
        this.itoProcessId = taskRequest.getItoProcessId();
        this.assigneeConfirmation = taskRequest.getAssigneeConfirmation();
    }
}