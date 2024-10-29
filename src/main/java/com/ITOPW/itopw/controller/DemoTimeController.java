package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.service.DbSysTimeResponse;
import com.ITOPW.itopw.service.DbSysTimeService;
import com.ITOPW.itopw.service.DemoTimeService;
import com.ITOPW.itopw.service.DemoTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class DemoTimeController {

    private final DemoTimeService demoTimeService;
    private final DbSysTimeService dbSysTimeService; // 추가된 부분

    @Autowired
    public DemoTimeController(DemoTimeService demoTimeService, DbSysTimeService dbSysTimeService) {
        this.demoTimeService = demoTimeService;
        this.dbSysTimeService = dbSysTimeService; // 초기화 추가
    }

    @GetMapping("/current-datetime")
    public DemoTimeResponse getCurrentDateTime() {
        LocalDateTime now = demoTimeService.getCurrentDateTime();
        return new DemoTimeResponse(now);
    }

    @GetMapping("/dbsystime")
    public DbSysTimeResponse getDbSysTime() {
        String dbsystime = dbSysTimeService.fetchDbSysTime(); // dbSysTimeService를 사용
        return new DbSysTimeResponse(dbsystime);
    }
}