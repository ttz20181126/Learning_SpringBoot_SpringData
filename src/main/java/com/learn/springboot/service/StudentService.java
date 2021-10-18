package com.learn.springboot.service;

import com.learn.springboot.pojo.Student;

import java.util.List;

public interface StudentService {

    /**
     * 添加用户
     * @param student
     */
    void addStudent(Student student);

    /**
     * 查询用户列表
     * @return
     */
    List<Student> findStudentAll();


    Student findById(Integer id);

    void updateStudent(Student student);
}