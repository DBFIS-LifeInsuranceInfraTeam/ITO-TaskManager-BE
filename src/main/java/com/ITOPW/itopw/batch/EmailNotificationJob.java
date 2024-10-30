package com.ITOPW.itopw.batch;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.repository.TaskRepository;
import com.ITOPW.itopw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmailNotificationJob {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    // 매일 자정에 스케줄링
    //@Scheduled(cron = "0 0 0 * * ?")
    public void sendDueDateNotifications() {
        // 마감일이 하루 남은 작업 조회
        LocalDate nextDay = LocalDate.now().plusDays(1);
        List<Task> tasksDueTomorrow = taskRepository.findByDueDate(nextDay);

        for (Task task : tasksDueTomorrow) {
            // 담당자 조회
            User assignee = userRepository.findById(task.getAssigneeId()).orElse(null);
            if (assignee != null && assignee.getEmail() != null) {
                // 이메일 전송
                sendEmail(assignee.getEmail(), task.getTaskName());
            }
        }
    }

    private void sendEmail(String recipientEmail, String taskName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("작업 마감일 안내");
        message.setText(taskName + " 작업이 마감 하루 남았습니다.");

        mailSender.send(message);
    }
}