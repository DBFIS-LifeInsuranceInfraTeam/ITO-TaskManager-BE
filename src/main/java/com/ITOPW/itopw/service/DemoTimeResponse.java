package com.ITOPW.itopw.service;


//Not a Service
import java.time.LocalDateTime;

public class DemoTimeResponse {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public DemoTimeResponse(LocalDateTime dateTime) {
        this.year = dateTime.getYear();
        this.month = dateTime.getMonthValue();
        this.day = dateTime.getDayOfMonth();
        this.hour = dateTime.getHour();
        this.minute = dateTime.getMinute();
        this.second = dateTime.getSecond();
    }

    // Getters and Setters
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }
    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
    public int getSecond() { return second; }
    public void setSecond(int second) { this.second = second; }
}