package com.learn.springboot;

import com.learn.springboot.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springboot测试类
 * @runwith:启动器
 * SpringJUnit4ClassRunner.class 让junit与spring环境进行整合。
 *
 * @SpringBootTest(classes = {Application.class}):
 * 1.当前类为springboot的启动类  2.加载springboot的启动类，启动springboot
 *
 * junit与spring整合写测试类：
 * @ContextConfiguration("classpath:application-context.xml")
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class ApplicationTests {

    @Autowired
    private StudentService studentService;

    @Test
    public void contextLoads() {
        studentService.springBootTestInsert();
    }

}
