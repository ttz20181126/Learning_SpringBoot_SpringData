package com.learn.springboot.service.impl;

import com.learn.springboot.pojo.StudentJpa;
import com.learn.springboot.service.StudentJpaEhcacheService;
import com.learn.springboot.service.StudentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentJpaEhcacheServiceImpl implements StudentJpaEhcacheService {



    @Autowired
    private StudentJpaRepository studentJpaRepository;


    @Override
    public List<StudentJpa> findStudentAll() {
        return studentJpaRepository.findAll();
    }

    @Override
    @Cacheable(value = "defineBySelf") //对当前查询的对象做缓存处理,pojo要实现Serializable,支持缓存到磁盘
    public StudentJpa findStudentById(Integer id) {
        return studentJpaRepository.findById(id).get();
    }

    @Override
    public Page<StudentJpa> findStudentByPage(Pageable pageable) {
        return studentJpaRepository.findAll(pageable);
    }

    @Override
    public void saveStudent(StudentJpa studentJpa) {
        studentJpaRepository.save(studentJpa);
    }
}