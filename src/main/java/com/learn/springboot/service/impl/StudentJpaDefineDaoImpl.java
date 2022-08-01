package com.learn.springboot.service.impl;


import com.learn.springboot.pojo.Student;
import com.learn.springboot.pojo.StudentJpa;
import com.learn.springboot.service.StudentJpaDefineRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class StudentJpaDefineDaoImpl implements StudentJpaDefineRepository {

    @PersistenceContext(name = "entityManagerFactory")
    private EntityManager em;

    //@Autowired
    //private Student student;

    @Override
    public StudentJpa findStudentJpaById(Integer id) {
        return this.em.find(StudentJpa.class,id);
    }
}
