package com.learn.springboot.controller.scheduler;

import com.learn.springboot.pojo.Student;
import com.learn.springboot.service.StudentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class QuartzDemo implements Job {

    @Autowired
    private StudentService studentService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Quartz定时任务方法触发: " + new Date());

        //启动报错。QuartzConfig类中关联job对象factoryBean.setJobClass(QuartzDemo.class);利用的反射，这时候QuartzDemo不在spring容器中，无法注入studentService。
        studentService.virtualAddStudent(new Student("王八蛋",18));
    }
}