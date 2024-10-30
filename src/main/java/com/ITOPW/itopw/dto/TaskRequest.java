package com.ITOPW.itopw.dto;

import java.time.LocalDate;

public class TaskRequest {
    private String taskId; // Integer에서 String으로 변경
    private Integer projectId;
    private String taskName;
    private String description;
    private String assigneeId;
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Integer frequencyId;
    private Integer commentCount; // 필요 없다면 제거 가능
    private Integer status;
    private Integer itoProcessId;
    private String assigneeConfirmation;

    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
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
