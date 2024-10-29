package com.ITOPW.itopw.service;

import com.ITOPW.itopw.repository.DbSysTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DbSysTimeService {

    private final DbSysTimeRepository repository;

    @Autowired
    public DbSysTimeService(DbSysTimeRepository repository) {
        this.repository = repository;
    }

    public String fetchDbSysTime() {
        Instant dbTime = repository.getDbSysTime(); // Instant 타입으로 현재 시간 가져오기

        // Instant를 String으로 변환
        String formattedTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(dbTime);

        return formattedTime;  // 변환된 String 반환
    }
}
