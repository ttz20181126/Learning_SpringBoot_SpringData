package com.learn.springboot.view;

import com.learn.springboot.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * springboot整合jsp
 */
@Controller
public class JspViewController {

    /***
     * 处理用户请求
     * note：通过视图解析器，跳转到webapp/WEB-INF/jsp/userList.jsp页面。
     * 【当前项目新建了templates目录,springboot会默认去找templates下，导致失效，在自己的单体项目中不会】
     * @param model
     * @return
     */
    @RequestMapping("/showUser")
    public String showUser(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, 12, "wangwu"));
        list.add(new User(2, 14, "caodan"));
        model.addAttribute("userList", list);
        return "userList";
    }

}