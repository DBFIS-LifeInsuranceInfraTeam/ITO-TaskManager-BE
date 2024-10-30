package com.ITOPW.itopw.config;

import com.ITOPW.itopw.batch.EmailNotificationJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail emailNotificationJobDetail() {
        return JobBuilder.newJob(EmailNotificationJob.class)
                .withIdentity("emailNotificationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger emailNotificationJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(emailNotificationJobDetail())
                .withIdentity("emailNotificationTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 18 * * ?")) // 매일 저녁 6시 실행
                .build();
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) throws Exception {
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(emailNotificationJobDetail(), emailNotificationJobTrigger());
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }
}
