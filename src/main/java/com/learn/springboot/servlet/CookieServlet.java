package com.learn.springboot.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CookieServlet",urlPatterns = "/cookieServlet")
public class CookieServlet extends HttpServlet {

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

        html += "<tr><th>编号:</th><td>" + 1 + "</td></tr>";
        html += "<tr><th>商品名称:</th><td>" + "丝巾" + "</td></tr>";
        html += "<tr><th>商品型号:</th><td>" + "拉维斯" + "</td></tr>";
        html += "<tr><th>商品价格:</th><td>" + "￥1000" + "</td></tr>";


        html += "</table>";
        html += "<center><a href='" + req.getContextPath()+"/firstServlet'>[返回列表]</a></center>";
        html += "</body>";
        html += "</html>";

        Cookie cookie = new Cookie("setOurCookieKey","setOurCookieValue");
        //过期时间5分钟
        cookie.setMaxAge(5 * 60);
        //2.发送cookie
        response.addCookie(cookie);
        //等同于response.addHeader("Set-Cookie","setOurCookieKey=setOurCookieValue");

        writer.write(html);

        //templates 安全的,需要controller跳转
        //response.sendRedirect("/templates/show_student.html");
    }
}