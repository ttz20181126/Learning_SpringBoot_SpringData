package com.learn.springboot.service;


import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/***
 * JpaSpecificationExecutor接口
 * (ps:java单继承，但是接口可以多继承)
 */
public interface StudentJPASpecificationExecutor extends JpaRepository<StudentJpa,Integer>, JpaSpecificationExecutor<StudentJpa> {
}