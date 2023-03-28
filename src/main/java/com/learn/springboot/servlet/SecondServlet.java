package com.learn.springboot.servlet;


import com.learn.springboot.pojo.Student;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * springboot集成servlet方式二
 * Application2.java中实例化这个servlet
 */
public class SecondServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------------second servlet------------");
        //super.doGet(req, resp);

        HttpSession session = req.getSession();
        if(session != null){
            //请求头带有Cookie: JSESSIONID=43414A3FBD0AFE2DC7C52E9B804AD9F9
            /*for (Cookie cookie : req.getCookies()) {
               if(cookie.getName().equals("JSESSIONID")){
                   System.out.println(cookie.getValue());
               }
            }*/
            //请求头带有Cookie: JSESSIONID=43414A3FBD0AFE2DC7C52E9B804AD9F9
            Student s = (Student)session.getAttribute("SessionTestKey");
            System.out.println("======session:=======" + s.getName());
        }

        resp.sendRedirect("upload.html");
    }
}