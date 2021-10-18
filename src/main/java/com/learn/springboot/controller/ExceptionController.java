package com.learn.springboot.controller;


import org.springframework.web.bind.annotation.RequestMapping;

/**
 * springboot处理异常方式一：自定义错误页面。
 */
public class ExceptionController {


    /**
     * 出现错误，向/error请求，springboot的BasicExceptionController处理请求
     * ~~~~~~默认的错误页面对用户不太友好~~~~~~~~~~~~
     * @return
     */
    @RequestMapping("/defaultExceptionPage")
    public String defaultExceptionPage(){
        String str = null;
        str.length();
        return "ok";
    }
}