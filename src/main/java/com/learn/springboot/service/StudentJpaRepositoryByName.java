package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository接口的方法名称命令查询
 */
public interface StudentJpaRepositoryByName extends Repository<StudentJpa,Integer> {


    /**
     * 方法的名称必须要遵循驼峰式命令规则:
     *     findBy(关键字) + 属性名称(首字母要大写) + 查询条件(首字母要大写):findByNameEquals\findByNameIs
     * @param name
     * @return
     */
    List<StudentJpa> findByName(String name);


    List<StudentJpa> findByNameAndAge(String name,Integer age);

    List<StudentJpa> findByNameOrAge(String keyword,Integer age);

    List<StudentJpa> findByNameLike(String name);

}