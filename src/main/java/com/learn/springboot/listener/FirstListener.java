package com.learn.springboot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * springboot整合Listener
 *
 * 以往web.xml配置：
 * <listener>
 *     <listener-class>com.learn.springboot.listener.FirstListener</listener-class>
 * </listener>
 */
@WebListener
public class FirstListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //项目启动时直接打印。
        System.out.println("*******FirstListener*************init**********");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}