package com.learn.springboot.mapper;

import com.learn.springboot.pojo.Student;

import java.util.List;

public interface StudentMapper {

    void insertStudent(Student students);


    List<Student> selectStudentAll();
}