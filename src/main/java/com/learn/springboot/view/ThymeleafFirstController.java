package com.learn.springboot.view;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/****
 * Thymeleaf入门案例
 */
@Controller
public class ThymeleafFirstController {


    /**
     * thymeleaf是通过他特定的语法对html标记做渲染。创建html页面即可。
     * @param model
     * @return
     */
    @RequestMapping("/helloThymeleaf")
    public String showInfo(Model model){
        model.addAttribute("msg","thymeleaf第一个案列");
        return  "thymeleaf";
    }



}