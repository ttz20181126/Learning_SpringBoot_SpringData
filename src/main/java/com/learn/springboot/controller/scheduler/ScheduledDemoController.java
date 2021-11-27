package com.learn.springboot.controller.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledDemoController {


    /**
     * 定时任务方法
     *
     * @Scheduled 设置定时任务
     * (cron = "")  定时任务触发是时间的一个字符串表达式
     * cron表达式分6个域分别代表： 秒  分  时 日  月  星期
     * 0/2 表示步长，从0开始，每隔2秒
     * 2 12 * * * * 每小时的12点的2秒
     * ？就是占位，舍弃这个值。     *标识任意
     */
    @Scheduled(cron = "0/2 * * *  * ?")
    public void scheduledMethod(){
        System.out.println("定时任务触发:" + new Date());
    }


}