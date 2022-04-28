package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentJpaDefineDao extends JpaRepository<StudentJpaDefineDao,Integer>, JpaSpecificationExecutor<StudentJpa>,StudentJpaDefineRepository {
}

