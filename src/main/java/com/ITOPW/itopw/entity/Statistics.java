package com.ITOPW.itopw.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable(false) // 엔티티 레벨에서 2차 캐시 비활성화
@Table(name = "statistics_view")
public class Statistics {

    @Id
    @Column(name = "project_id")
    private String projectId;

    @Column(name = "month")
    private String month;

    @Column(name = "total_tasks")
    private Integer totalTasks;

    @Column(name = "before_percentage")
    private BigDecimal beforePercentage;

    @Column(name = "progress_percentage")
    private BigDecimal progressPercentage;

    @Column(name = "complete_percentage")
    private BigDecimal completePercentage;

    @Column(name = "delayed_percentage")
    private BigDecimal delayedPercentage;

    @Column(name = "previous_before_percentage")
    private BigDecimal previousBeforePercentage;

    @Column(name = "previous_progress_percentage")
    private BigDecimal previousProgressPercentage;

    @Column(name = "previous_complete_percentage")
    private BigDecimal previousCompletePercentage;

    @Column(name = "previous_delayed_percentage")
    private BigDecimal previousDelayedPercentage;

    @Override
    public String toString() {
        return "Statistics{" +
                "projectId='" + projectId + '\'' +
                ", month=" + month +
                ", totalTasks=" + totalTasks +
                ", beforePercentage=" + beforePercentage +
                ", progressPercentage=" + progressPercentage +
                ", completePercentage=" + completePercentage +
                ", delayedPercentage=" + delayedPercentage +
                ", previousBeforePercentage=" + previousBeforePercentage +
                ", previousProgressPercentage=" + previousProgressPercentage +
                ", previousCompletePercentage=" + previousCompletePercentage +
                ", previousDelayedPercentage=" + previousDelayedPercentage +
                '}';
    }
}

