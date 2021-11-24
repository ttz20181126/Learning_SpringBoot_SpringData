package com.learn.springboot.service.impl;

import com.learn.springboot.pojo.StudentJpa;
import com.learn.springboot.service.StudentJpaEhcacheService;
import com.learn.springboot.service.StudentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentJpaEhcacheServiceImpl implements StudentJpaEhcacheService {



    @Autowired
    private StudentJpaRepository studentJpaRepository;


    @Override
    @Cacheable(value = "defineBySelf")
    public List<StudentJpa> findStudentAll() {
        return studentJpaRepository.findAll();
    }

    /**
     * @Cacheable 把方法的返回值添加到Ehcache中做缓存
     *    value属性：指定一个Ehcache配置文件中的缓存策略，如没有给定value,name则表示使用默认的缓存策略
     *    key属性: 给存储的值起个名称，在查询时如果有名称相同的，那么这从缓存中将数据返回。
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "defineBySelf") //对当前查询的对象做缓存处理,pojo要实现Serializable,支持缓存到磁盘
    public StudentJpa findStudentById(Integer id) {
        return studentJpaRepository.findById(id).get();
    }

    @Override
    //@Cacheable(value = "defineBySelf",key = "#pageable")
    @Cacheable(value = "defineBySelf",key = "#pageable.pageSize")
    public Page<StudentJpa> findStudentByPage(Pageable pageable) {
        return studentJpaRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "defineBySelf",allEntries = true)  //清除以defineBySelf策略存储的缓存。
    public void saveStudent(StudentJpa studentJpa) {
        studentJpaRepository.save(studentJpa);
    }
}