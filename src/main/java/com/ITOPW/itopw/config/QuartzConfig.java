package com.ITOPW.itopw.config;

import com.ITOPW.itopw.batch.EmailNotificationJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import org.quartz.spi.JobFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfig {


    @Autowired
    private ApplicationContext applicationContext; // ApplicationContext 주입

    @Bean
    public JobFactory jobFactory() {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext); // ApplicationContext 설정
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory());
        return factory;
    }

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
                // 필요에 따라 변경하여 테스트
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 1 1 ?"))
                .build();
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) throws Exception {
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(emailNotificationJobDetail(), emailNotificationJobTrigger());
        return scheduler;
    }

//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() {
//        return new SchedulerFactoryBean();
//    }
}
