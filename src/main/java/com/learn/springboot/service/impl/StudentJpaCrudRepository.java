package com.learn.springboot.service.impl;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.repository.CrudRepository;

public interface StudentJpaCrudRepository extends CrudRepository<StudentJpa,Integer> {



}