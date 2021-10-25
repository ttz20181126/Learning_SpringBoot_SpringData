package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.repository.CrudRepository;

public interface StudentJpaCrudRepository extends CrudRepository<StudentJpa,Integer> {



}