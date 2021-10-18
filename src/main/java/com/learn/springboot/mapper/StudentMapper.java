package com.learn.springboot.mapper;

import com.learn.springboot.pojo.Student;

import java.util.List;

public interface StudentMapper {

    void insertStudent(Student students);


    List<Student> selectStudentAll();

    Student selectStudentById(Integer id);

    void updateStudent(Student student);

    void  deleteStudentById(Integer id);
}
