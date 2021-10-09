package com.learn.springboot.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


/**
 * springboot整合Filter方式一
 *
 * 以往在web.xml配置：
 * <filter>
 *     <filter-name>FirstFilter</filter-name>
 *     <filter-class>com.learn.springboot.filter.FirstFilter</filter-class>
 * </filter>
 * <filter-mapping>
 *     <filter-name>FirstFilter</filter-name>
 *     <url-pattern>/first</url-pattern>
 * </filter-mapping>
 */
//@WebFilter(filterName = "FirstFilter",urlPatterns = {"*.do","*.jsp"})
@WebFilter(filterName = "FirstFilter",urlPatterns = "/firstServlet")
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    /***
     * 访问/firstServlet
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("~~~~~~~~进入Filter~~~~~~~");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("~~~~~~~~~离开Filter~~~~~~~~~");
    }

    @Override
    public void destroy() {

    }
}