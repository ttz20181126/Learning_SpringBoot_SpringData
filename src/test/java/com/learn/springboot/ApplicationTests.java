package com.learn.springboot;

import com.learn.springboot.pojo.*;
import com.learn.springboot.service.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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

    //Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.22.2:test (default-test) on project Learning_SpringBoot_SpringData
    //类上面加注解让maven打包跳过测试类，或者pom中配置，skipTrue为true。注解会让后续真是执行测试方法时候无法执行直接滤过。推荐pom中配置。
    //@Ignore

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

    @Autowired
    private StudentJPASpecificationExecutor studentJPASpecificationExecutor;

    @Autowired
    private UserMappingJpaRepository userMappingJpaRepository;

    @Autowired
    private RoleMappingJpaRepository roleMappingJpaRepository;

    @Autowired
    private StudentJpaEhcacheService studentJpaEhcacheService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private EntityManager entityManager;

    //@Autowired
    //private SessionFactory sessionFactory;


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
     *
     * CrudRepository的save()实现有transcation，所以测试类中不用
     */
    @Test
    public void testJpaCrudRepository(){
        //保存
        StudentJpa studentJpa = new StudentJpa();
        studentJpa.setAddress("北京是朝阳区");
        studentJpa.setAge(11);
        studentJpa.setName("么么哒c");
        studentJpaCrudRepository.save(studentJpa);

        //更新
        //save也是更新也是保存
        StudentJpa studentJpa2 = new StudentJpa();
        studentJpa2.setId(3);
        studentJpa2.setAddress("北京是朝阳区");
        studentJpa2.setAge(11);
        studentJpa2.setName("么么哒a");
        studentJpaCrudRepository.save(studentJpa2);

        //查询-通过id查询
        Optional<StudentJpa> byId = studentJpaCrudRepository.findById(4);

        //查询list
        Iterable<StudentJpa> all = studentJpaCrudRepository.findAll();
        Iterator<StudentJpa> iterator = all.iterator();
        while (iterator.hasNext()){
            StudentJpa next = iterator.next();
            System.out.println(next.getName());
        }
        List<StudentJpa> list = (List<StudentJpa>)studentJpaCrudRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName());
        }

        //删除
        //studentJpaCrudRepository.deleteById(4);
    }


    /***
     * hibernate数据的临时、游离、持久化
     * session关闭，数据持久化，不调用save也自动保存到数据库。
     * findById在实现中没有加事务，就不会对象持久化，test默认方法回滚，需要加上rollback，这样就可以演示现象。
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testJpaCrudRepository02(){
        StudentJpa studentJpa = studentJpaCrudRepository.findById(4).get();
        studentJpa.setName("持久化");
    }

    /**
     * springboot集成spring data jpa  之 PagingAndSortingRepository 接口测试
     */
    @Test
    public void testPagingAndSortingRepository(){

        //********************************1.sort排序方法**************************
        /***
         * 单列排序
         * sort：该对象封装了排序规则以及指定的排序字段-字段的属性来表示；
         * direction：排序规则；
         * properties：指定做排序的属性。
         */
        Sort singleSort = new Sort(Sort.Direction.DESC,"age");
        List<StudentJpa> singList = (List<StudentJpa>)this.studentJPagingAndSortingRepository.findAll(singleSort);
        for(StudentJpa jpa : singList){
            System.out.println("迭代结果：" + jpa);
        }

        /**
         * 多列排序
         */
        Sort.Order orderMul1 = new Sort.Order(Sort.Direction.DESC,"id");
        Sort.Order orderMul2 = new Sort.Order(Sort.Direction.DESC,"age");
        Sort sort = new Sort(orderMul1,orderMul2);
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
     *
     * JpaRepository接口时我们开发时使用最多的接口，其特点时可以帮助我们将其他接口的方法的返回值做适配处理，
     * 可以使得我们开发时更方便的使用这些方法。
     */
    @Test
    public void testJpaRepository(){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"id");
        Sort sort = new Sort(order);
        //不用再像使用PagingAndSortingRepository或者CrudRepository强转
        List<StudentJpa> all = studentJpaRepository.findAll(sort);
        for(StudentJpa s1 : all){
            System.out.println("当前页数据 : " + s1);
        }
    }

    /**
     * springboot集成spring data jpa  之  JPASpecificationExecutor
     */
    @Test
    public void testStudentJPASpecificationExecutor(){
        //****************************************1.单条件查询*****************************************************
        //Specification封装查询条件。Predicate封装单个查询条件.
        Specification<StudentJpa> spec = new Specification<StudentJpa>() {

            /**
             * Predicate 定义了查询条件。
             * @param root   根对象，查询对象的属性封装。
             * @param criteriaQuery   封装我们要执行的查询中的各个部分的信息。select、from、order，定义了一个基本的查询，一般不使用。
             * @param criteriaBuilder  查询条件的构造器，定义不同的查询条件。
             * @return
             */
            @Override
            public Predicate toPredicate(Root<StudentJpa> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name = ‘张三’
                Predicate pred = criteriaBuilder.equal(root.get("name"), "张三");
                return pred;
            }
        };
        List<StudentJpa> all = this.studentJPASpecificationExecutor.findAll(spec);
        for(StudentJpa s1 : all){
            System.out.println("当前结果数据 : " + s1);
        }



        //****************************************2.多条件查询  方式一*****************************************************
        //where name = '张三' and age = 11
        Specification<StudentJpa> spec2 = new Specification<StudentJpa>() {
            @Override
            public Predicate toPredicate(Root<StudentJpa> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name = '张三' and age = 11
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("name"), "张三"));
                list.add(criteriaBuilder.equal(root.get("age"), 11));
                //此时查询条件没有任何关系
                Predicate[] arr = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(arr));
            }
        };
        List<StudentJpa> all2 = this.studentJPASpecificationExecutor.findAll(spec2);
        for(StudentJpa s1 : all2){
            System.out.println("当前结果数据 : " + s1);
        }


        //****************************************3.多条件查询(其他写法)   方式二****************************************
        //where name = '张三' and age = 11
        Specification<StudentJpa> spec3 = new Specification<StudentJpa>() {
            @Override
            public Predicate toPredicate(Root<StudentJpa> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name = '张三' and age = 11
                return criteriaBuilder.and(criteriaBuilder.equal(root.get("name"),"张三"),criteriaBuilder.equal(root.get("age"),11));
            }
        };
        List<StudentJpa> all3 = this.studentJPASpecificationExecutor.findAll(spec3);
        for(StudentJpa s1 : all3){
            System.out.println("当前结果数据 : " + s1);
        }

        //where name = '张三' or age = 11
        Specification<StudentJpa> spec4 = new Specification<StudentJpa>() {
            @Override
            public Predicate toPredicate(Root<StudentJpa> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name = '张三' or age = 11
                return criteriaBuilder.or(criteriaBuilder.equal(root.get("name"),"张三"),criteriaBuilder.equal(root.get("age"),11));
            }
        };
        List<StudentJpa> all4 = this.studentJPASpecificationExecutor.findAll(spec4);
        for(StudentJpa s1 : all4){
            System.out.println("当前结果数据 : " + s1);
        }

        //where (name = '张三' and  age = 15) or id = 3
        Specification<StudentJpa> spec5 = new Specification<StudentJpa>() {
            @Override
            public Predicate toPredicate(Root<StudentJpa> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where (name = '张三' and  age = 15) or id = 3
                return criteriaBuilder.or((criteriaBuilder.and(criteriaBuilder.equal(root.get("name"),"张三"),criteriaBuilder.equal(root.get("age"),11))),criteriaBuilder.equal(root.get("id"),3));
            }
        };
        List<StudentJpa> all5 = this.studentJPASpecificationExecutor.findAll(spec5);
        for(StudentJpa s1 : all5){
            System.out.println("当前结果数据 : " + s1);
        }

        //where (name = '张三' and  age = 15) or id = 3 limit 0,2
        Specification<StudentJpa> spec6 = new Specification<StudentJpa>() {
            @Override
            public Predicate toPredicate(Root<StudentJpa> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where (name = '张三' and  age = 15) or id = 3
                return criteriaBuilder.or((criteriaBuilder.and(criteriaBuilder.equal(root.get("name"),"张三"),criteriaBuilder.equal(root.get("age"),11))),criteriaBuilder.equal(root.get("id"),3));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<StudentJpa> all6 = this.studentJPASpecificationExecutor.findAll(spec6,sort);
        for(StudentJpa s1 : all6){
            System.out.println("当前结果数据 : " + s1);
        }

    }


    /**
     * spring data jpa的一对多关系的保存
     */
    @Test
    public void testOneToManySave(){
        //创建一个用户
        UserMapping userMapping = new UserMapping();
        userMapping.setName("Mr");
        userMapping.setAddress("深圳市");
        userMapping.setAge(18);
        //创建一个角色
        RoleMapping roleMapping = new RoleMapping();
        roleMapping.setRoleName("部长");
        //关联
        userMapping.setRoleMapping(roleMapping);
        roleMapping.getUsers().add(userMapping);
        //保存 --UserMapping中@ManyToOne后添加(cascade = CascadeType.PERSIST) 级联保存
        userMappingJpaRepository.save(userMapping);
    }

    /**
     * 一对多关系的查询
     */
    @Test
    public void testOneToManyFind(){
        UserMapping userMapping = userMappingJpaRepository.findById(1).get();
        System.out.println(userMapping);
        RoleMapping roleMapping = userMapping.getRoleMapping();
        System.out.println(roleMapping);
    }


    @Test
    public void testManyToManySave(){
        //创建角色
        RoleMapping roleMapping = new RoleMapping();
        roleMapping.setRoleName("项目经理");

        //创建菜单
        MenusMapping menusMapping = new MenusMapping();
        menusMapping.setMenusName("Springboot教程");
        menusMapping.setParentId(0);

        MenusMapping menusMapping2 = new MenusMapping();
        menusMapping2.setMenusName("第一章");
        menusMapping2.setParentId(1);

        //关联
        roleMapping.getMenus().add(menusMapping);
        roleMapping.getMenus().add(menusMapping2);

        menusMapping.getRoles().add(roleMapping);
        menusMapping2.getRoles().add(roleMapping);

        //保存
        roleMappingJpaRepository.save(roleMapping);
    }


    /****
     * 多对多的级联操作之查询
     */
    @Test
    public void testManyToManyFind(){

        /***
         * 直接运行报错：could not initialize proxy - no Session
         * 原因：spring data jpa底层还是hibernate，hibernate默认延迟加载，查询角色后再去查询菜单，和数据库的会话已经关闭了。
         * 处理：延迟加载(懒加载，级联操作等发送需求再查询)该立即加载。fetch = FetchType.EAGER
         *
         */
        RoleMapping roleMapping = roleMappingJpaRepository.findById(2).get();
        System.out.println("rolename:" + roleMapping.getRoleName());
        Set<MenusMapping> menus = roleMapping.getMenus();
        for (MenusMapping menusMapping : menus){
            System.out.println(menusMapping);
        }

    }

    /**
     * 测试ehcache
     * 没有@Cacheable(  查看日志，发现查询两次。
     * 添加@Cacheable 只查询一次。
     */
    @Test
    public void testEhcache(){
        StudentJpa studentById = studentJpaEhcacheService.findStudentById(1);
        System.out.println("1:" + studentById);

        StudentJpa studentById2 = studentJpaEhcacheService.findStudentById(1);
        System.out.println("2:" + studentById2);
    }


    /**
     * @Cacheable(value = "defineBySelf",key = "#pageable")
     *      1,2查询一次     3查询一次
     *
     * @Cacheable(value = "defineBySelf",key = "#pageable.pageSize")
     *      1,2,3共查询一次
     */
    @Test
    public void testEhcacheKeyField(){
        Pageable  pageable = new PageRequest(0,2);
        long totalElements = this.studentJpaEhcacheService.findStudentByPage(pageable).getTotalElements();
        System.out.println("1:" + totalElements);
        long totalElements2 = this.studentJpaEhcacheService.findStudentByPage(pageable).getTotalElements();
        System.out.println("2:" + totalElements2);

        pageable = new PageRequest(1,2);
        long totalElements3 = this.studentJpaEhcacheService.findStudentByPage(pageable).getTotalElements();
        System.out.println("3:" + totalElements3);
    }


    /**
     * 测试CacheEvict
     *
     * 没有evict，中间插入了一条数据，但是两次打印的条数仍然一样。
     */
    @Test
    public void testCacheEvict(){
        System.out.println("1:" + studentJpaEhcacheService.findStudentAll().size());

        StudentJpa studentJpa = new StudentJpa();
        studentJpa.setName("jessica");
        studentJpa.setAge(18);
        studentJpa.setAddress("武汉");
        studentJpaEhcacheService.saveStudent(studentJpa);

        System.out.println("2:" + studentJpaEhcacheService.findStudentAll().size());
    }


    /**
     * spring data redis测试
     */
    @Test
    public void testSpringDateRedis(){
        //添加一个字符串
        this.redisTemplate.opsForValue().set("key","getech");

        //获取
        String value = (String)this.redisTemplate.opsForValue().get("key");
        System.out.println("redis-value:" + value);
    }

    /**
     * spring data redis存对象--以JDK序列化形式存
     * 重新设置序列化器件
     * 对象要实现序列化接口 implements Serializable。
     * 打开redis管理器，可以看到乱码是正常的：对象序列化成字节，然后以字符形式存在redis
     *
     * 【直接使用序列化器避免我们以往使用json转化】
     */
    @Test
    public void testSetObject(){
        User user = new User();
        user.setUserId(1);
        user.setUserAge(18);
        user.setUserName("王五");
        //重新设置序列化器,否则:com.learn.springboot.pojo.User cannot be cast to java.lang.String
        this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        this.redisTemplate.opsForValue().set("user",user);

        //如果在另外方法取值，要设置序列化器，以相同的序列化拿
        User value = (User)this.redisTemplate.opsForValue().get("user");
        System.out.println(value);
    }

    /**
     * 以json形式存实体对象，比jdk序列化器更省空间
     */
    @Test
    public void testSetJsonObject(){
        User user = new User();
        user.setUserId(2);
        user.setUserAge(18);
        user.setUserName("王Json五");

        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        this.redisTemplate.opsForValue().set("user2",user);

        User value = (User)this.redisTemplate.opsForValue().get("user2");
        System.out.println(value);

    }


    /**
     * 证明springboot中注入的是entityManager不是sessionFactory.
     */
    @Test
    public void testEntityManagerSourceFrom(){
        //org.springframework.data.jpa.repository.support.SimpleJpaRepository@368e7d3a
        System.out.println(this.userMappingJpaRepository);
        //class com.sun.proxy.$Proxy128
        System.out.println(this.userMappingJpaRepository.getClass());

        JpaRepositoryFactory factory  = new JpaRepositoryFactory(entityManager);
        //getRepository(UsersDao.class);可以帮助我们为接口生成实现类，而这个实现类是SimpleJpaRepository，
        //要求该接口必须要是继承Repository接口。
        UserMappingJpaRepository ud = factory.getRepository(UserMappingJpaRepository.class);
        System.out.println(ud);//org.springframework.data.jpa.repository.support.SimpleJpaRepository@eb77241
        System.out.println(ud.getClass());//class com.sun.proxy.$Proxy123
    }
}
