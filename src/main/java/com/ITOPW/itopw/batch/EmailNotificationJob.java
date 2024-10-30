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
        LocalDate nextDay = LocalDate.now().plusDays(1);
        List<Task> tasksDueTomorrow = taskRepository.findByDueDate(nextDay);

        if (tasksDueTomorrow.isEmpty()) {
            System.out.println("마감일이 하루 남은 작업이 없습니다.");
            return;
        }

        for (Task task : tasksDueTomorrow) {
            try {
                User assignee = userRepository.findById(task.getAssigneeId()).orElseThrow(() ->
                        new Exception("담당자를 찾을 수 없습니다. Task ID: " + task.getTaskId()));
                if (assignee.getEmail() != null) {
                    sendEmail(assignee.getEmail(), task.getTaskName());
                } else {
                    System.out.println("담당자의 이메일이 없습니다. Task ID: " + task.getTaskId());
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }
        }
    }

    private void sendEmail(String recipientEmail, String taskName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("작업 마감일 안내");
        message.setText(taskName + " 작업이 마감 하루 남았습니다.");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("메일 전송 실패: " + e.getMessage());
        }
    }
}