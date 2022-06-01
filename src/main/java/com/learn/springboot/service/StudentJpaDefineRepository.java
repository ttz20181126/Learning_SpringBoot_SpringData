package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;

public interface StudentJpaDefineRepository {

    StudentJpa findStudentJpaById(Integer id);
}
