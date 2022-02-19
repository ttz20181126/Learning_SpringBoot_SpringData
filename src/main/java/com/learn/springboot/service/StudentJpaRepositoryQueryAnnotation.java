package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository接口的@Query注解使用
 */
public interface StudentJpaRepositoryQueryAnnotation extends Repository<StudentJpa,Integer> {


    /**
     * 注意：是from类名，不是表名。
     * @param name
     * @return
     */
    @Query("from StudentJpa where name = :name")
    List<StudentJpa> queryByNameUseHQL(String name);


    /**
     * JPA默认是使用HQL，使用sql查询时候需要nativeQuery属性
     * nativeQuery默认false，表示不对value值做转义。
     * @param name
     * @return
     */
    @Query(value = "select * from t_students where name = ?",nativeQuery = true)
    List<StudentJpa> queryByNameUseSql(String name);

    /**
     * @Query 支持查询，需要@Modifying
     * @param name
     * @param id
     */
    //@Query("update StudentJpa set name = ? where id = ?")低版本支持
    @Query("update StudentJpa set name = :name where id = :id")
    @Modifying
    void updateUsersNameById(String name,Integer id);

}