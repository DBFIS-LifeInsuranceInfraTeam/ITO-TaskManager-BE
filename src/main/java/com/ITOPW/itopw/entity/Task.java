package com.ITOPW.itopw.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;

@Entity
@Table(name = "TASK")  // DB 테이블 이름과 매핑
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "assignee_id") // text로 변경
    private String assigneeId;  // String으로 변경

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "frequency_id")
    private Integer frequencyId;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "status")
    private Integer status;

    @Column(name = "ito_process_id")
    private Integer itoProcessId;

    @Column(name = "assignee_confirmation") // text로 변경
    private String assigneeConfirmation; // String으로 변경

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
