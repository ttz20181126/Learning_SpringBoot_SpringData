package com.learn.springboot.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//TODO 为了验证ResolverException类中的SimpleMappingExceptionResolver暂时注释掉。
//@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ModelAndView nullExceptionHandler(Exception e){
        ModelAndView mv = new  ModelAndView();
        mv.addObject("error",e.toString());
        mv.setViewName("error_null");
        return mv;
    }

    @ExceptionHandler(value = {java.lang.ArithmeticException.class})
    public ModelAndView arithmeticExceptionHandler(Exception e){
        ModelAndView mv = new  ModelAndView();
        mv.addObject("error2",e.toString());
        mv.setViewName("error_arithmetic");
        return mv;
    }


}