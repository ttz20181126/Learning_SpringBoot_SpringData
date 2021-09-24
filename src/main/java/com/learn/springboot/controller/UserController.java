package com.learn.springboot.controller;

import com.learn.springboot.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/***
 * @ClassName UserController
 * @Description TODO
 * @Author Taycen
 * @Date 2019/8/22 0022 16:46
 * @Jdk 1.8
 ***/
@Controller
public class UserController {
    @RequestMapping("/userList")
    public String userList(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, 12, "zhangsan"));
        list.add(new User(2, 14, "mazi"));
        model.addAttribute("userList", list);
        //优先默认去找templates目录下找,找不到才会找识图解析器"组装"的
        return "userList";
    }

    @RequestMapping("/showUser")
    public String showUser(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, 12, "wangwu"));
        list.add(new User(2, 14, "caodan"));
        model.addAttribute("userList", list);
        //return "forward:userList.ftl"失效   默认去找templates
        return "userList2";
    }
}
