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
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "task_id", nullable = false) // PK, NOT NULL
    private String taskId;

    @Column(name = "project_id", nullable = false) // NOT NULL
    private Integer projectId;

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
    @Builder.Default
    private Integer commentCount = 0; // @Builder.Default 추가

    @Column(name = "status", nullable = false) // NOT NULL
    private Integer status;

    @Column(name = "ito_process_id", nullable = false) // NOT NULL
    private Integer itoProcessId;

    @Column(name = "assignee_confirmation", nullable = false) // NOT NULL
    private String assigneeConfirmation;
}
