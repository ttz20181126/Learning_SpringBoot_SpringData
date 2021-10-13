package com.learn.springboot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/***
 * @ClassName UserController
 * @Description TODO
 * @Author Taycen
 * @Date 2019/8/22 0022 16:46
 * @Jdk 1.8
 ***/
@SpringBootApplication
@ServletComponentScan  //在springboot启动时会扫描@WebServlet 、 @WebFilter
@MapperScan("com.learn.springboot.mapper") //用户扫描mybatis的mapper接口，生成代理对象。
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
