package com.ITOPW.itopw.controller;

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

    @Autowired
    public DemoTimeController(DemoTimeService demoTimeService) {
        this.demoTimeService = demoTimeService;
    }

    @GetMapping("/current-datetime")
    public DemoTimeResponse getCurrentDateTime() {
        LocalDateTime now = demoTimeService.getCurrentDateTime();
        return new DemoTimeResponse(now);
    }
}