package com.learn.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * springboot集成servlet方式一
 *
 * <servlet>
 *     <servlet-name>FirstServlet</servlet-name>
 *     <servlet-class>com.learn.springboot.servlet.FirstServlet</servlet-class>
 * </servlet>
 * <servlet>
 *     <servlet-name>FirstServlet</servlet-name>
 *     <url-pattern>/firstServlet</url-pattern>
 * </servlet>
 * 等效替代sprintboot集成的tomcat包下的：@WebServlet(name = "FirstServlet",urlPatterns = "/firstServlet")
 */
@WebServlet(name = "FirstServlet",urlPatterns = "/firstServlet")
public class FirstServlet  extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------------first servlet------------");
        super.doGet(req, resp);
    }
}