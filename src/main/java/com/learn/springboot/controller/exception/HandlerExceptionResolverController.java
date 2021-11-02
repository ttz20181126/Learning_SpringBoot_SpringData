package com.learn.springboot.controller.exception;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过实现HandlerExceptionResolver处理异常返回异常信息
 */
//@Configuration
//public class HandlerExceptionResolverController implements HandlerExceptionResolver {
//
//    @Override
//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
//        ModelAndView mv = new ModelAndView();
//        if(ex instanceof ArithmeticException){
//            mv.setViewName("error_conf_arithmetic");
//        }
//        if(ex instanceof NullPointerException){
//            mv.setViewName("error_conf_null");
//        }
//        mv.addObject("errorMessage", ex.toString());
//        return mv;
//    }
//}

public class HandlerExceptionResolverController{

}