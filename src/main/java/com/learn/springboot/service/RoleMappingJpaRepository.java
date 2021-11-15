package com.learn.springboot.service;

import com.learn.springboot.pojo.RoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleMappingJpaRepository extends JpaRepository<RoleMapping,Integer> {
}