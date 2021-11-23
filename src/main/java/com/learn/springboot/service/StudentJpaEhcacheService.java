package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentJpaEhcacheService {

    List<StudentJpa> findStudentAll();
    StudentJpa findStudentById(Integer id);
    Page<StudentJpa> findStudentByPage(Pageable pageable);
    void saveStudent(StudentJpa studentJpa);


}
