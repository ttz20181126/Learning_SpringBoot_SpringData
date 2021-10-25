package com.learn.springboot;

import com.learn.springboot.pojo.StudentJpa;
import com.learn.springboot.service.*;
import com.learn.springboot.service.StudentJpaCrudRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * springboot测试类
 * @runwith:启动器
 * SpringJUnit4ClassRunner.class 让junit与spring环境进行整合。
 *
 * @SpringBootTest(classes = {Application.class}):
 * 1.当前类为springboot的启动类  2.加载springboot的启动类，启动springboot
 *
 * junit与spring整合写测试类：
 * @ContextConfiguration("classpath:application-context.xml")
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class ApplicationTests {

    @Autowired
    private StudentService studentService;


    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private StudentJpaRepositoryByName studentJpaRepositoryByName;

    @Autowired
    private StudentJpaRepositoryQueryAnnotation studentJpaRepositoryQueryAnnotation;

    @Autowired
    private StudentJpaCrudRepository studentJpaCrudRepository;

    @Autowired
    private StudentJPagingAndSortingRepository studentJPagingAndSortingRepository;



    @Test
    public void contextLoads() {
        studentService.springBootTestInsert();
    }

    /**
     * springboot集成spring data jpa  之  JpaRepository
     * 逆向生成表，并打印sql
     * Hibernate: insert into t_users (address, age, name) values (?, ?, ?)
     */
    @Test
    public void testSpringDataJpa() {
        StudentJpa studentJpa = new StudentJpa();
        studentJpa.setAddress("北京是朝阳区");
        studentJpa.setAge(11);
        studentJpa.setName("李四");
        studentJpaRepository.save(studentJpa);
    }


    /**
     * springboot集成spring data jpa  之 Repository方法名称命名测试
     */
    @Test
    public void testJpaRepositoryByMethodName(){
        List<StudentJpa> list = studentJpaRepositoryByName.findByName("张三");
        for(StudentJpa s : list){
            System.out.println("list:"+ s);
        }

        List<StudentJpa> list2 = studentJpaRepositoryByName.findByNameAndAge("张三",4);
        for(StudentJpa s : list2){
            System.out.println("list2:"+ s);
        }
        if(list2.size() == 0){
            System.out.println("list2 为 null");
        }



        List<StudentJpa> list3 = studentJpaRepositoryByName.findByNameOrAge("李四",5);
        for(StudentJpa s : list3){
            System.out.println("list3:"+ s);
        }
        if(list3.size() == 0){
            System.out.println("lsit3 为 null");
        }


        List<StudentJpa> list4 = studentJpaRepositoryByName.findByNameLike("张%");
        for(StudentJpa s : list4){
            System.out.println("list4:"+ s);
        }
    }

    /**
     * springboot集成spring data jpa  之 Repository接口的@Query测试
     *
     * @Query 只支持查询，需要update语句，要加上@Modifying
     *
     * update必须在事务中，需要@Transactional注解，但是@Transactional和@Test在一起会事务回滚，需要@Rollback注解
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testQueryAnnotation(){
        List<StudentJpa> list = studentJpaRepositoryQueryAnnotation.queryByNameUseHQL("张三");
        for(StudentJpa studentJpa : list){
            System.out.println(studentJpa);
        }

        List<StudentJpa> list2 = studentJpaRepositoryQueryAnnotation.queryByNameUseSql("张三");
        for(StudentJpa studentJpa : list2){
            System.out.println(studentJpa);
        }

        studentJpaRepositoryQueryAnnotation.updateUsersNameById("李思",2);

    }

    /**
     * springboot集成spring data jpa  之 CrudRepository接口测试
     */
    @Test
    public void testJpaCrudRepository(){
        StudentJpa studentJpa = new StudentJpa();
        studentJpa.setAddress("北京是朝阳区");
        studentJpa.setAge(11);
        studentJpa.setName("么么哒c");
        studentJpaCrudRepository.save(studentJpa);

        //save也是更新也是保存
        StudentJpa studentJpa2 = new StudentJpa();
        studentJpa2.setId(3);
        studentJpa2.setAddress("北京是朝阳区");
        studentJpa2.setAge(11);
        studentJpa2.setName("么么哒a");
        studentJpaCrudRepository.save(studentJpa2);

        //Optional<StudentJpa> byId = studentJpaCrudRepository.findById(4);
        //Iterable<StudentJpa> all = studentJpaCrudRepository.findAll();
        //studentJpaCrudRepository.deleteById(4);
    }


    /**
     * springboot集成spring data jpa  之 PagingAndSortingRepository 接口测试
     */
    @Test
    public void testPagingAndSortingRepository(){

        //********************************1.sort排序方法**************************
        //order定义了排序规则
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"id");
        //sort对象封装了排序规则,多个排序规则可以装多个order.
        Sort sort = new Sort(order);
        List<StudentJpa> list = (List<StudentJpa>)this.studentJPagingAndSortingRepository.findAll(sort);
        for(StudentJpa jpa : list){
            System.out.println("迭代结果：" + jpa);
        }

        //**************************************2.page分页方法*************************************
        //Pageable封装了分页参数：当前页，每页显示条数.注意：当前页从0开始。
        // new PageRequest(int page, int size)//当前页，每页多少
        Pageable pageable = new PageRequest(0, 2);
        Page<StudentJpa> all = this.studentJPagingAndSortingRepository.findAll(pageable);
        System.out.println("总条数:" + all.getTotalElements()  + ",总页数： " + all.getTotalPages());
        List<StudentJpa> content = all.getContent();
        for(StudentJpa s : content){
            System.out.println("当前页数据 : " + s);
        }


        //**************************************3.分页+排序*************************************
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"id");
        Sort sort1 = new Sort(order1);
        Pageable pageable1 = new PageRequest(0, 2,sort1);
        Page<StudentJpa> all2 = this.studentJPagingAndSortingRepository.findAll(pageable1);
        for(StudentJpa s1 : all2.getContent()){
            System.out.println("当前页数据 : " + s1);
        }
    }

    /**
     * springboot集成spring data jpa  之  JpaRepository
     */
    @Test
    public void testJpaRepository(){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"id");
        Sort sort = new Sort(order);
        //不用再像使用PagingAndSortingRepository强转
        List<StudentJpa> all = studentJpaRepository.findAll(sort);
        System.out.println(all);
    }

}
