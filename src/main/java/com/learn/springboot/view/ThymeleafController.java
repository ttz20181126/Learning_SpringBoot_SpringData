package com.learn.springboot.view;


import com.learn.springboot.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/****
 * Thymeleaf入门案例
 */
@Controller
public class ThymeleafController {


    /**
     * thymeleaf是通过他特定的语法对html标记做渲染。创建html页面即可。
     * @param model
     * @return
     */
    @RequestMapping("/helloThymeleaf")
    public String showInfo(Model model, HttpServletRequest request){
        model.addAttribute("msg","thymeleaf第一个案列");
        model.addAttribute("key",new Date());
        model.addAttribute("sex","男");
        model.addAttribute("id","2");

        List<User> list = new ArrayList<>();
        list.add(new User(1, 12, "wangwu"));
        list.add(new User(2, 14, "caodan"));
        model.addAttribute("list",list);

        Map<String, User> map = new HashMap<>();
        map.put("u1",new User(1, 12, "wangwu"));
        map.put("u2",new User(2, 14, "caodan"));
        model.addAttribute("map",map);

        request.setAttribute("req","HttpServletRequest");
        request.getSession().setAttribute("sess","HttpSession");
        request.getSession().getServletContext().setAttribute("app","Application");

        return  "thymeleaf";
    }

    /**
     * 请求XXXX,跳转到XXXX页面
     * @param path
     * @return
     */
    //@RequestMapping("/{page}")
    //public String forwardParameterPage(@PathVariable String path){
    //    return path;
    //}

}