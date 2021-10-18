package com.learn.springboot.controller;


import com.learn.springboot.pojo.Student;
import com.learn.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {


    @Autowired
    private StudentService studentService;


    /**
     * 添加学生
     * localhost:8080/students/add_student
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("/addStudent")
    public String addStudent(Student student){
        this.studentService.addStudent(student);
        return "ok";
    }


    /**
     * 查询学生列表
     * localhost:8080/students/findStudentAll
     * @param model
     * @return
     */
    @RequestMapping("/findStudentAll")
    public String findStudentAll(Model model){
        List<Student> studentAll = this.studentService.findStudentAll();
        model.addAttribute("list",studentAll);
        return "showStudents";
    }


}