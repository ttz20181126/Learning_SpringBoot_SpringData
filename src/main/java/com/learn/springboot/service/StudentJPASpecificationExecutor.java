package com.learn.springboot.service;


import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/***
 * JpaSpecificationExecutor接口
 * (ps:java单继承，但是接口可以多继承)
 *
 * JpaSpecificationExecutor不能单独使用，需要配合jpa中其他接口一起使用，因为它没有继承任何jpa接口，但是spring data jpa的原理就是通过继承Repository的接口通过JpaRepositoryFactory生成接口的代理对象。
 */
public interface StudentJPASpecificationExecutor extends JpaRepository<StudentJpa,Integer>, JpaSpecificationExecutor<StudentJpa> {
}