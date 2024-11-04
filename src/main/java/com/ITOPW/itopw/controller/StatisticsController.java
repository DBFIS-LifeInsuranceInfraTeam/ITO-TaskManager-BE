package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.entity.Statistics;
import com.ITOPW.itopw.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<?> getStatisticsByProjectIdAndMonth(
            @RequestParam List<String> projectIds,
            @RequestParam(required = false) String month) {
        StatisticsService.StatisticsResponse response = statisticsService.calculateStatistics(projectIds, month);
        return ResponseEntity.ok(response);
    }


}
