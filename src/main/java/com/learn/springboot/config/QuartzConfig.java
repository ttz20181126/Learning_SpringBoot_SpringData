package com.learn.springboot.config;


import com.learn.springboot.controller.scheduler.QuartzDemo;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Objects;

/****
 * Quartz配置类
 */
@Configuration
public class QuartzConfig {

    /**
     * 1.创建job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        //关联自己的job类
        factoryBean.setJobClass(QuartzDemo.class);
        return factoryBean;
    }


    /***
     * 2.创建trigger对象-simpleTrigger
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        //关联jobDetail对象
        factoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
        //执行Trigger,执行5次,一个执行间隔2000毫秒
        factoryBean.setRepeatInterval(2000);
        factoryBean.setRepeatCount(5);
        return factoryBean;
    }

    /**
     * 2.创建trigger对象-cronTrigger
     * @param jobDetailFactoryBean
     * @return
     */
    /*public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
        cronTriggerFactoryBean.setCronExpression("0/2 * * * * ?");
        return cronTriggerFactoryBean;
    }*/


    /****
     * 3.创建scheduler对象
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        return schedulerFactoryBean;
    }
}