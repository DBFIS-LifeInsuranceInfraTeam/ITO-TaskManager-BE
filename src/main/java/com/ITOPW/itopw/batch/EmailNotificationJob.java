package com.ITOPW.itopw.batch;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.repository.TaskRepository;
import com.ITOPW.itopw.repository.UserRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Component
public class EmailNotificationJob implements Job {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalDate nextDay = LocalDate.now().plusDays(1);
        List<Task> tasksDueTomorrow = taskRepository.findByDueDate(nextDay);

        if (tasksDueTomorrow.isEmpty()) {
            System.out.println("마감일이 하루 남은 작업이 없습니다.");
            return;
        }

        for (Task task : tasksDueTomorrow) {
            try {
                User assignee = userRepository.findByUserId(task.getAssigneeId()).orElseThrow(() ->
                        new Exception("담당자를 찾을 수 없습니다. Task ID: " + task.getTaskId()));

                if (assignee.getEmail() != null) {
                    sendStyledEmailWithImage(assignee.getEmail(), task, assignee);
                } else {
                    System.out.println("담당자의 이메일이 없습니다. Task ID: " + task.getTaskId());
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }
        }
    }


    private void sendStyledEmailWithImage(String recipientEmail, Task task, User assignee) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("작업 마감일 안내");
            helper.setFrom("DB ITO <db.itopw@gmail.com>");

            String htmlContent = "<div style='text-align: center; padding: 20px;'>"
                    + "<a href='http://메인페이지추가필요.com'>" // 링크 추가
                    + "<img src='cid:logoImage' style='width: 100px; height: auto;'>"
                    + "</a>"
                    + "<h1>작업 마감일 알림</h1>"
                    + "<br>"
                    + "<p style='font-size: 17px;'>"
                    + task.getTaskName() + "의 마감이 하루 남았습니다!</p>"
                    + "<p>작업명: <b>" + task.getTaskName() + "</b></p>"
                    + "<p>담당자: <b>" + assignee.getName() + "</b></p>"
                    + "<p>마감일: <b>" + task.getDueDate() + "</b></p>"
                    + "</div>";


            helper.setText(htmlContent, true);

            File imageFile = new File("src/main/resources/static/images/DB_Logo.png");
            helper.addInline("logoImage", imageFile);

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("메일 전송 실패: " + e.getMessage());
        }
    }


}
