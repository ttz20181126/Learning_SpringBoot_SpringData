package com.learn.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @ClassName ThymeleafController
 * @Description TODO
 * @Author Taycen
 * @Date 2019/8/23 0023 17:11
 * @Jdk 1.8
 ***/
@Controller
public class ThymeleafController {
    @RequestMapping("/show")
    public String showInfo(Model model){
        model.addAttribute("msg","Thymeleaf第一个案例");
        return "index";
    }
}
