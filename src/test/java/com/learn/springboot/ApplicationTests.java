package com.learn.springboot;

import com.learn.springboot.pojo.StudentJpa;
import com.learn.springboot.service.StudentJpaRepository;
import com.learn.springboot.service.StudentJpaRepositoryByName;
import com.learn.springboot.service.StudentJpaRepositoryQueryAnnotation;
import com.learn.springboot.service.StudentService;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
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

    @Test
    public void contextLoads() {
        studentService.springBootTestInsert();
    }

    /**
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
     * Repository方法名称命名测试
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
     * Repository接口的@Query测试
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


}
