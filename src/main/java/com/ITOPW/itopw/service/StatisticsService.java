package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Statistics;
import com.ITOPW.itopw.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public Optional<Statistics> getStatisticsByProjectIdAndMonth(String projectId, LocalDate month) {
        return statisticsRepository.findByProjectIdAndMonth(projectId, month);
    }
}

