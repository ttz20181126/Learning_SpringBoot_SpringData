package com.learn.springboot.service;


import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 继承类的泛型解释
 * 当前需要映射的实体
 * 当前映射的实体中的主键ID的类型
 */
public interface StudentJpaRepository extends JpaRepository<StudentJpa,Integer> {


    //JpaRepository有很多方法。

}