package com.learn.springboot.servlet;


import com.learn.springboot.pojo.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "SessionServlet",urlPatterns = "/sessionServlet")  //启动Application2.java 注入bean不扫描注解
public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(req, resp);
        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();
        String html = "";

        html += "<html>";
        html += "<head>";
        html += "<title>显示商品详细</title>";
        html += "</head>";
        html += "<body>";
        html += "<table border='1' align='center' width='300px'>";

        for (int i = 0; i < 10; i++) {
            html += "<tr><th>编号:</th><td>" + i + "</td></tr>";
            html += "<tr><th>商品名称:</th><td>" + "丝巾"  + i + "</td></tr>";
            html += "<tr><th>商品型号:</th><td>" + "拉维斯" + + i + "</td></tr>";
            html += "<tr><th>商品价格:</th><td>" + "￥1000" + i +  "</td></tr>";

        }

        html += "</table>";
        html += "<center><a href='" + req.getContextPath()+"/secondServlet'>[返回列表]</a></center>;";
        html += "</body></html>";

        //response.addHeader("Set-Cookie","JSESSIONID2BA0D71184D63A09C15CB18F8E088DAA");
        //响应头:Set-Cookie: JSESSIONID=2BA0D71184D63A09C15CB18F8E088DAA; Path=/; HttpOnly
        //和cookies的一点区别,session保存在服务器端,可以用对象
        req.getSession().setAttribute("SessionTestKey",new Student(1,"tingTing",18));

        writer.write(html);

    }
}