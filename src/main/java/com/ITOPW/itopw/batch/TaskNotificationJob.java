package com.ITOPW.itopw.batch;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.repository.TaskRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@EnableBatchProcessing
@Component
@EnableScheduling
public class TaskNotificationJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public Job sendTaskNotificationJob() {
        return jobBuilderFactory.get("sendTaskNotificationJob")
                .incrementer(new RunIdIncrementer())
                .flow(sendTaskNotificationStep())
                .end()
                .build();
    }

    @Bean
    public Step sendTaskNotificationStep() {
        return stepBuilderFactory.get("sendTaskNotificationStep")
                .tasklet((contribution, chunkContext) -> {
                    LocalDate tomorrow = LocalDate.now().plusDays(1);
                    List<Task> tasks = taskRepository.findByDueDate(tomorrow);

                    for (Task task : tasks) {
                        sendEmail(task);
                    }
                    return null;
                })
                .build();
    }

    private void sendEmail(Task task) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(task.getAssigneeId()); // 담당자 이메일
        message.setSubject("업무 알림: " + task.getTaskName());
        message.setText("안녕하세요,\n\n업무 '" + task.getTaskName() + "'가 내일 마감입니다.\n\n감사합니다.");
        mailSender.send(message);
    }

    // 매일 자정에 Job 실행
    @Scheduled(cron = "0 0 0 * * ?")
    public void runJob() throws Exception {
        sendTaskNotificationJob().execute(null);
    }
}
