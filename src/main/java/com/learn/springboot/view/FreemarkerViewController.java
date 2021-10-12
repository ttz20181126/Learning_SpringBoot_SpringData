package com.learn.springboot.view;

import com.learn.springboot.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FreemarkerViewController {

    /**
     * 优先默认去找templates目录下找,找不到才会找识图解析器"组装"的
     * @param model
     * @return
     */
    @RequestMapping("/userList")
    public String userList(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, 12, "zhangsan"));
        list.add(new User(2, 14, "mazi"));
        model.addAttribute("userList", list);
        return "userList";
    }
}