package com.ITOPW.itopw.batch;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.repository.TaskRepository;
import com.ITOPW.itopw.repository.UserRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Component
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
        //List<Task> tasksDueTomorrow = taskRepository.findByDueDate(nextDay);
        String taskId = "TASK_21ac9cd1";
//        Optional<Task> tasksDueTomorrow = taskRepository.findByTaskId(taskId);
        Optional<Task> tasksDueTomorrow = taskRepository.findTaskWithAssignees(taskId);


        if (tasksDueTomorrow.isEmpty()) {
            System.out.println("마감일이 하루 남은 작업이 없습니다.");
            return;
        }

//        for (Task task : tasksDueTomorrow) {
//            try {
//                User assignee = userRepository.findByUserId(task.getAssignees()).orElseThrow(() ->
//                        new Exception("담당자를 찾을 수 없습니다. Task ID: " + task.getTaskId()));
//
//                if (assignee.getEmail() != null) {
//                    sendStyledEmailWithImage(assignee.getEmail(), task, assignee);
//                } else {
//                    System.out.println("담당자의 이메일이 없습니다. Task ID: " + task.getTaskId());
//                }
//            } catch (Exception e) {
//                System.out.println("오류 발생: " + e.getMessage());
//            }
//        }


        Task task = tasksDueTomorrow.get();
        Set<User> assigneeIds = task.getAssignees(); // Task의 assigneeIds 가져오기

        if (assigneeIds == null || assigneeIds.isEmpty()) {
            System.out.println("Task ID " + taskId + "에 대한 담당자가 없습니다.");
            return;
        }

        for (User assigneeId : assigneeIds) {
            try {
                // 담당자(User) 조회
                User assignee = userRepository.findByUserId(assigneeId.getUserId())
                        .orElseThrow(() -> new Exception("담당자를 찾을 수 없습니다. User ID: " + assigneeId));

                // 이메일 발송
                if (assignee.getEmail() != null && !assignee.getEmail().isEmpty()) {
                    sendStyledEmailWithImage(assignee.getEmail(), task, assignee);
                    System.out.println("이메일 발송 완료: " + assignee.getEmail());
                } else {
                    System.out.println("담당자의 이메일이 없습니다. User ID: " + assigneeId);
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
                    + "<a href='http://210.127.59.84:31427'>" // 링크 추가
                    + "<img src='cid:logoImage' style='width: 100px; height: auto;'>"
                    + "</a>"
                    + "<h1>작업 마감일 알림</h1>"
                    + "<br>"
                    + "<p style='font-size: 19px;'>"
                    + task.getTaskName() + "의 마감이 하루 남았습니다!</p>"
                    + "<div style='display: inline-block; text-align: left;'>"
                    + "<p style='margin: 0; font-size: 17px;'>작업명: <b>" + task.getTaskName() + "</b></p>"
                    + "<p style='margin: 0; font-size: 17px;'>담당자: <b>" + assignee.getName() + "</b></p>"
                    + "<p style='margin: 0; font-size: 17px;'>마감일: <b>" + task.getDueDate() + "</b></p>"
                    + "</div>"
                    + "<br><br>"
                    + "<a href='http://210.127.59.84:31427/api/tasks/" + task.getTaskId() + "/confirm?confirmation=Y'>"
                    + "<button style='"
                    + "    padding: 10px 20px; "
                    + "    background: linear-gradient(145deg, #6a9dff, #4CAF50); "
                    + "    color: white; "
                    + "    border: none; "
                    + "    font-size: 18px; "
                    + "    cursor: pointer; "
                    + "    border-radius: 8px; "
                    + "    box-shadow: 4px 4px 6px rgba(0, 0, 0, 0.2), -4px -4px 6px rgba(255, 255, 255, 0.5); "
                    + "    transition: all 0.3s ease; "
                    + "'>확인완료</button>"
                    + "</a>"
                    + "</div>";


            helper.setText(htmlContent, true);


            ClassPathResource imageResource = new ClassPathResource("static/images/DB_Logo.png");
            helper.addInline("logoImage", imageResource);

//            File imageFile = new File("app/images/DB_Logo.png");
//            helper.addInline("logoImage", imageFile);

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("메일 전송 실패: " + e.getMessage());
            e.printStackTrace();  // 예외의 세부사항을 출력
        }
    }


}
