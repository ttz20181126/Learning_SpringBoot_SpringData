package com.learn.springboot;


import com.learn.springboot.servlet.SecondServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

/***
 * @ClassName UserController
 * @Description TODO
 * @Author Taycen
 * @Date 2019/8/22 0022 16:46
 * @Jdk 1.8
 ***/
@SpringBootApplication
@ServletComponentScan  //在springboot启动时会扫描@WebServlet
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /**
     * 替代@WebServlet(name = "SecondServlet",urlPatterns = "/secondServlet")
     * @return
     */
    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(new SecondServlet());
        bean.addUrlMappings("/secondServlet");
        return bean;

    }

}
