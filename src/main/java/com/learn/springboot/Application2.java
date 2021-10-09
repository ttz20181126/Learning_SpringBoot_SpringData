package com.learn.springboot;


import com.learn.springboot.filter.SecondFilter;
import com.learn.springboot.listener.SecondListener;
import com.learn.springboot.servlet.SecondServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import javax.servlet.Servlet;

@SpringBootApplication
public class Application2 {

    /**
     * 注册servlet
     *
     * 替代@WebServlet(name = "SecondServlet",urlPatterns = "/secondServlet")
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(new SecondServlet());
        System.out.println("~~~~注册servlet~~~");
        bean.addUrlMappings("/secondServlet");
        return bean;

    }

    /**
     * 注册Filter
     *
     * @WebFilter(filterName = "SecondFilter",urlPatterns = "/secondServlet")
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new SecondFilter());
        //bean.addUrlPatterns(new String[]{"*.do","*.jsp"});
        System.out.println("~~~~注册filter~~~");
        bean.addUrlPatterns("/secondServlet");
        return bean;

    }

    /**
     * 注册listener
     *
     * @WebListener
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean getListenRegistrationBean(){
        ServletListenerRegistrationBean<SecondListener> bean = new ServletListenerRegistrationBean<SecondListener>(new SecondListener());
        return bean;
    }
}