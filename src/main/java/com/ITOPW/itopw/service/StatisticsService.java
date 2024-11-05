package com.ITOPW.itopw.service;

import com.ITOPW.itopw.dto.response.StatisticsResponse;
import com.ITOPW.itopw.entity.Statistics;
import com.ITOPW.itopw.repository.StatisticsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StatisticsRepository statisticsRepository;

    public StatisticsResponse calculateStatistics(List<String> projectIds, String month) {


        YearMonth requestedMonth = YearMonth.parse(month);
        String previousMonth = requestedMonth.minusMonths(1).toString(); // 이전 달을 "YYYY-MM" 형식의 문자열로 변환
        String currentMonth = requestedMonth.toString();

//        int totalCurrentTasks = 0;
//        int totalPreviousTasks = 0;
//        int totalCurrentBeforeTasks = 0;
//        int totalPreviousBeforeTasks = 0;

        List<Statistics> currentStats = statisticsRepository.findByProjectIdAndMonth(projectIds, currentMonth);
        entityManager.clear();
        List<Statistics> previousStats = statisticsRepository.findByProjectIdAndMonth(projectIds, previousMonth);
        entityManager.clear();

        // 현재 월 (11월) 데이터
        int currentTotalTasks = 0;
        int currentBeforeTasks = 0;
        int currentProgressTasks = 0;
        int currentCompleteTasks = 0;
        int currentDelayedTasks = 0;

        for (Statistics stat : currentStats) {
            currentTotalTasks += stat.getTotalTasks();
            currentBeforeTasks += stat.getTotalTasks() * stat.getBeforePercentage().divide(BigDecimal.valueOf(100)).intValue();
            currentProgressTasks += stat.getTotalTasks() * stat.getProgressPercentage().divide(BigDecimal.valueOf(100)).intValue();
            currentCompleteTasks += stat.getTotalTasks() * stat.getCompletePercentage().divide(BigDecimal.valueOf(100)).intValue();
            currentDelayedTasks += stat.getTotalTasks() * stat.getDelayedPercentage().divide(BigDecimal.valueOf(100)).intValue();
        }

        // 이전 월 (10월) 데이터
        int previousTotalTasks = 0;
        int previousBeforeTasks = 0;
        int previousProgressTasks = 0;
        int previousCompleteTasks = 0;
        int previousDelayedTasks = 0;

        for (Statistics stat : previousStats) {
            previousTotalTasks += stat.getTotalTasks();
            previousBeforeTasks += stat.getTotalTasks() * stat.getBeforePercentage().divide(BigDecimal.valueOf(100)).intValue();
            previousProgressTasks += stat.getTotalTasks() * stat.getProgressPercentage().divide(BigDecimal.valueOf(100)).intValue();
            previousCompleteTasks += stat.getTotalTasks() * stat.getCompletePercentage().divide(BigDecimal.valueOf(100)).intValue();
            previousDelayedTasks += stat.getTotalTasks() * stat.getDelayedPercentage().divide(BigDecimal.valueOf(100)).intValue();
        }

        // 비율 계산
        BigDecimal currentBeforePercentage = currentTotalTasks > 0
                ? BigDecimal.valueOf((double) currentBeforeTasks / currentTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal currentProgressPercentage = currentTotalTasks > 0
                ? BigDecimal.valueOf((double) currentProgressTasks / currentTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal currentCompletePercentage = currentTotalTasks > 0
                ? BigDecimal.valueOf((double) currentCompleteTasks / currentTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal currentDelayedPercentage = currentTotalTasks > 0
                ? BigDecimal.valueOf((double) currentDelayedTasks / currentTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        BigDecimal previousBeforePercentage = previousTotalTasks > 0
                ? BigDecimal.valueOf((double) previousBeforeTasks / previousTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal previousProgressPercentage = previousTotalTasks > 0
                ? BigDecimal.valueOf((double) previousProgressTasks / previousTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal previousCompletePercentage = previousTotalTasks > 0
                ? BigDecimal.valueOf((double) previousCompleteTasks / previousTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal previousDelayedPercentage = previousTotalTasks > 0
                ? BigDecimal.valueOf((double) previousDelayedTasks / previousTotalTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        // 상태 증가율 계산
        BigDecimal beforeIncrease = currentBeforePercentage.subtract(previousBeforePercentage).setScale(2, RoundingMode.HALF_UP);
        BigDecimal progressIncrease = currentProgressPercentage.subtract(previousProgressPercentage).setScale(2, RoundingMode.HALF_UP);
        BigDecimal completeIncrease = currentCompletePercentage.subtract(previousCompletePercentage).setScale(2, RoundingMode.HALF_UP);
        BigDecimal delayedIncrease = currentDelayedPercentage.subtract(previousDelayedPercentage).setScale(2, RoundingMode.HALF_UP);

        // 최종 통합된 통계 생성
        Statistics combinedStatistics = Statistics.builder()
                .projectId(projectIds.toString())
                .month(month)
                .totalTasks(currentTotalTasks)
                .beforePercentage(currentBeforePercentage.multiply(BigDecimal.valueOf(100)))
                .progressPercentage(currentProgressPercentage.multiply(BigDecimal.valueOf(100)))
                .completePercentage(currentCompletePercentage.multiply(BigDecimal.valueOf(100)))
                .delayedPercentage(currentDelayedPercentage.multiply(BigDecimal.valueOf(100)))
                .previousBeforePercentage(previousBeforePercentage.multiply(BigDecimal.valueOf(100)))
                .previousProgressPercentage(previousProgressPercentage.multiply(BigDecimal.valueOf(100)))
                .previousCompletePercentage(previousCompletePercentage.multiply(BigDecimal.valueOf(100)))
                .previousDelayedPercentage(previousDelayedPercentage.multiply(BigDecimal.valueOf(100)))
                .build();

        return new StatisticsResponse(combinedStatistics, beforeIncrease.multiply(BigDecimal.valueOf(100)), progressIncrease.multiply(BigDecimal.valueOf(100)),
                completeIncrease.multiply(BigDecimal.valueOf(100)), delayedIncrease.multiply(BigDecimal.valueOf(100)));
    }

//        // 출력
//        System.out.println("11월 Before 상태 비율: " + currentBeforePercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("10월 Before 상태 비율: " + previousBeforePercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("Before 상태 증가율: " + beforeIncrease.multiply(BigDecimal.valueOf(100)) + "%");
//
//        System.out.println("11월 Progress 상태 비율: " + currentProgressPercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("10월 Progress 상태 비율: " + previousProgressPercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("Progress 상태 증가율: " + progressIncrease.multiply(BigDecimal.valueOf(100)) + "%");
//
//        System.out.println("11월 Complete 상태 비율: " + currentCompletePercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("10월 Complete 상태 비율: " + previousCompletePercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("Complete 상태 증가율: " + completeIncrease.multiply(BigDecimal.valueOf(100)) + "%");
//
//        System.out.println("11월 Delayed 상태 비율: " + currentDelayedPercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("10월 Delayed 상태 비율: " + previousDelayedPercentage.multiply(BigDecimal.valueOf(100)) + "%");
//        System.out.println("Delayed 상태 증가율: " + delayedIncrease.multiply(BigDecimal.valueOf(100)) + "%");





}
