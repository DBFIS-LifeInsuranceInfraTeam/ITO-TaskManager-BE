package com.ITOPW.itopw.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationJob {

    @Autowired
    private JavaMailSender emailSender;

    // 이메일 전송 메서드
    public void sendEmailNotification(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    // 특정 시간에 이메일 전송 (예: 매일 자정)
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void scheduleEmailNotification() {
        // 수신자, 제목, 본문을 설정
        String to = "recipient@example.com"; // 수신자 이메일
        String subject = "Task Due Reminder"; // 제목
        String body = "Your task is due tomorrow."; // 본문
        sendEmailNotification(to, subject, body);
    }
}
