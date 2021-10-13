package com.learn.springboot.controller;


import com.learn.springboot.pojo.Student;
import com.learn.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentController {


    @Autowired
    private StudentService studentService;


    /**
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



}