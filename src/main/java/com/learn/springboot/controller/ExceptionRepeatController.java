package com.learn.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 没有GlobalException.class,都跳转到error.html
 * 有了，这跳转到对应异常页面
 *    说明ExceptionController.class中的@ExceptionHandler(value = {java.lang.NullPointerException.class})
 * 注解异常处理只针对当前类有效。
 */
@Controller
public class ExceptionRepeatController {

    @RequestMapping("/defaultExceptionPage3")
    public String defaultExceptionPage3(){
        String str = null;
        str.length();
        return "ok";
    }

    @RequestMapping("/defaultExceptionPage4")
    public String defaultExceptionPage4(){
        int a = 10/0;
        return "ok";
    }
}