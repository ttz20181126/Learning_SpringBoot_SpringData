package com.learn.springboot.service;

import com.learn.springboot.pojo.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMappingJpaRepository extends JpaRepository<UserMapping,Integer> {
}