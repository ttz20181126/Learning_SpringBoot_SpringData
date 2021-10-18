package com.learn.springboot.service.impl;

import com.learn.springboot.mapper.StudentMapper;
import com.learn.springboot.pojo.Student;
import com.learn.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional //所有方法受事务控制
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void addStudent(Student student) {
        this.studentMapper.insertStudent(student);
    }

    @Override
    public List<Student> findStudentAll() {
        return this.studentMapper.selectStudentAll();
    }

    @Override
    public Student findById(Integer id) {
        return this.studentMapper.selectStudentById(id);
    }
}