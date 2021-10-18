package com.learn.springboot.controller;


import com.learn.springboot.pojo.Student;
import com.learn.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
     * @param students 避免页面th：errors的报错
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page,@ModelAttribute("student") Student students){
        return page;
    }

    /**
     * @Valid开启对象的数据校验
     * BindingResult  封装了校验结果
     * @param student
     * @return
     */
    @RequestMapping("/addStudent")
    public String addStudent(@Valid @ModelAttribute("student")Student student,BindingResult result){
        //校验失败
        if(result.hasErrors()){
            return "add_student";
        }
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

    /**
     * 修改学生信息回显
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/findStudentById")
    public String findStudentById(Integer id,Model model){
        Student stu = this.studentService.findById(id);
        model.addAttribute("stu",stu);
        return "updateStudent";
    }

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @RequestMapping("/editStudent")
    public String editStudent(Student student){
        studentService.updateStudent(student);
        return "ok";
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/delStudent")
    public String delStudent(Integer id){
        this.studentService.deleteStudentById(id);
        //删除用户后重定向查询列表
        return "redirect:/students/findStudentAll";
    }
}