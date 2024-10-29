package com.ITOPW.itopw.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TASK")  // DB 테이블 이름과 매핑
public class Task {

    @Id
    @Column(name = "task_id", nullable = false) // PK, NOT NULL
    private Integer taskId;

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
    private Integer commentCount = 0;  // 기본값 0으로 초기화

    @Column(name = "status", nullable = false) // NOT NULL
    private Integer status;

    @Column(name = "ito_process_id", nullable = false) // NOT NULL
    private Integer itoProcessId;

    @Column(name = "assignee_confirmation", nullable = false) // NOT NULL
    private String assigneeConfirmation;

    // Getters and Setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getItoProcessId() {
        return itoProcessId;
    }

    public void setItoProcessId(Integer itoProcessId) {
        this.itoProcessId = itoProcessId;
    }

    public String getAssigneeConfirmation() {
        return assigneeConfirmation;
    }

    public void setAssigneeConfirmation(String assigneeConfirmation) {
        this.assigneeConfirmation = assigneeConfirmation;
    }
}
