package com.learn.springboot.service;

import com.learn.springboot.pojo.StudentJpa;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * PagingAndSortingRepository接口的JPA操作
 *
 *
 * PagingAndSortingRepository提供了分页和排序操作：
 *  Iterable<T> findAll(Sort var1);
 *  Page<T> findAll(Pageable var1);
 */
public interface  StudentJPagingAndSortingRepository extends PagingAndSortingRepository<StudentJpa,Integer> {


}