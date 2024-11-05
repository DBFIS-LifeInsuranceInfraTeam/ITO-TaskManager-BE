package com.ITOPW.itopw.dto.response;

import com.ITOPW.itopw.entity.Statistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsResponse {
    private Statistics statistics;
    private BigDecimal beforeIncrease;
    private BigDecimal progressIncrease;
    private BigDecimal completeIncrease;
    private BigDecimal delayedIncrease;

    // Constructors, Getters, and Setters
    public StatisticsResponse(Statistics statistics, BigDecimal beforeIncrease, BigDecimal progressIncrease, BigDecimal completeIncrease, BigDecimal delayedIncrease) {
        this.statistics = statistics;
        this.beforeIncrease = beforeIncrease;
        this.progressIncrease = progressIncrease;
        this.completeIncrease = completeIncrease;
        this.delayedIncrease = delayedIncrease;
    }
}
