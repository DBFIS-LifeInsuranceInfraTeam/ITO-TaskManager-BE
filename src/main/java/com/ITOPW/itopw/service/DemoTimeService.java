package com.ITOPW.itopw.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DemoTimeService {

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}