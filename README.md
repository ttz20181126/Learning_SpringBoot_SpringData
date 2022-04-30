# springBoot_start_with_lesson
springboot入门教程  
https://pan.baidu.com/disk/main?from=oldversion#/index?category=all&path=%2FJAVA-%E8%8F%9C%E9%B8%9F%E5%88%B0%E7%8E%8B%E8%80%85%2Fspringboot%20%20springdata%E3%80%90%E7%99%BE%E6%88%98%E7%A8%8B%E5%BA%8F%E5%91%98%20%E5%BD%93%E5%89%8D%E5%AD%A6%E4%B9%A0%E3%80%91

# note
一、springboot访问静态资源
1.springboot从classpath/static的目录（目录名称必须是static）
   http://localhost:8080/girl.jpg
   http://localhost:8080/index.html
   http://localhost:8080/img/girl.jpg
2.ServletContext根目录下
  在src/main下创建webapp目录
  在webapp目录下如同static目录防止静态资源一样。
  http://localhost:8080/girl2.jpg
  http://localhost:8080/index2.html
  http://localhost:8080/img/girl2.jpg

二、springboot上传文件
有默认上传文件大小，可以在配置文件中修改。

三、整合视图层技术  
1.整合jsp
pom中添加jstl、jasper依赖;
配置视图解析器;
创建controller.

2.整合freemarker  
springboot要求模板形式的视图层技术的文件必须要放到src/main/resources目录下必须有一个名为templates的目录。

3.整合thymeleaf  
目录src/main/resources下templates;  
templates说明:该目录是安全的，不允许外界直接访问，必须是controller跳转。因为要做数据渲染。  
webapp下、classpath下的static文件夹下的静态资源非安全的;  
thymeleaf是通过他特定的语法对html标记做渲染;  
thymeleaf3.0以下的版本,对标签校验较严,参看thymeleaf.html中的说明;  

4.集成mybaits  
集成中，一定注意pom中要把mapper的xml打包到target中。  
```
<build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```

5.springboot的数据校验  
springboot的数据验证使用了Hibernate-validate校验框架  
  可以看到导入的jar文件中有一个hibernate-validator。  
  在校验属性上加@NotBlank等注解形式。  
  controller中添加@Valid注解开启对象的数据校验.    

  Q:el表达式，有就有，没有值就没有值，thymeleaf的th:errors如果没有会报错:  
  localhost:8080/students/add_student访问会报错了。  
  A：解决方法是在跳转页面的方法中注入一个Student对象。  
     由于springmvc会将该对象放入到Model对象中传递，key的名称会使用对象的驼峰式的命名规则来作为key，
     参数的变量名需要与对象的名称相同，将首字母小写。如果使用其他名称，可以使用@ModelAndAttribute注解.
     使用@ModelAndAttribute该名称了，等提交的时候校验如果不合法，又是返回到添加界面，所以添加的方法也要使用这个注解取同名。

  @NotBlank 判断字符串是否为null或者空串(去除首尾空格)
  @NotEmpty  判断字符串是否为null或者空串；
  @Length 判断字符的长度（最大或者最小）
  @min  @max  判断数值最大最小值。
  @Email判断邮箱是否合法。  @Email(regexp = "")可以添加更加详细的检验格式。


6.springboot中异常处理方式  
6.1 自定义错误页面 
    SpringBoot默认的处理异常的机制，springboot默认的已经提供了一套处理异常的机制，一旦程序中出现了异常springboot会向/error
的url发送请求，在springboot中提供了一个叫BasicExceptionController来处理/error请求，然后跳转到默认显示异常的页面来展示异常信息。
    需要将所有异常统一跳转到自定义的错误页面，需要在src/main/resources/templates创建error.html页面【注意：名称必须叫error】
    但是这种颗粒度比较粗，所有的错误都跳转到一个页面。

6.2 @ExceptionHandle注解处理异常  
   ~~~
    @ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ModelAndView nullExceptionHandler(Exception e){
            ModelAndView mv = new  ModelAndView();
            mv.addObject("error",e.toString());
            mv.setViewName("error_null");
            return mv;
     }
     //必须返回ModelAndView根据异常类型跳转对应页面。
    
    @ExceptionHandler(value = {java.lang.ArithmeticException.class})
    public ModelAndView arithmeticExceptionHandler(Exception e){}
   ~~~
     
6.3 @ControllerAdvice + @ExceptionHandle注解处理异常  
但是6.2的处理只针对当前controller的异常类有用。另外一个controller如果空指针异常仍然跳转到error.html，不是error_null.html.  
在类上将@Controller注解改为@ControllerAdvice则可针对全局做异常处理。  

6.4 配置SimpleMappingExceptionResolver处理异常  
对6.3的简化，6.3的处理上，需要对每一种异常编写对应的一个异常处理方式，  
6.4则将异常类和视图建立一个异常处理信息集合。一个方法搞定。
   ~~~
    @Configuration
    public class ResolverException {
        @Bean
        public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver(){
                SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
                Properties mappings = new Properties();
                mappings.put("java.lang.NullPointerException","error_conf_null");
                mappings.put("java.lang.ArithmeticException","error_conf_arithmetic");
                smer.setExceptionMappings(mappings);
                return smer;
        }
   ~~~
但是这个处理方式，无法给视图放回异常信息，6.3可以。  

6.5 自定义HandlerExceptionResolver。  
上述6.4简化了处理方式，但是不能返回异常信息给视图，  
   ~~~
    @Configuration
    public class HandlerExceptionResolverController implements HandlerExceptionResolver {
    
        @Override
        public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
            ModelAndView mv = new ModelAndView();
            if(ex instanceof ArithmeticException){
                mv.setViewName("error_conf_arithmetic");
            }
            if(ex instanceof NullPointerException){
                mv.setViewName("error_conf_null");
            }
            mv.addObject("errorMessage", ex.toString());
            return mv;
        }
    }
   ~~~

7 springboot热部署  
7.1 SpringLoader  
7.1.1 SpringLoader方式一  
pom中添加插件：
   ~~~
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <dependencies>
                <dependency>
                    <groupId>org.springframework</groupId>
                    <artifactId>springloaded</artifactId>
                    <version>1.2.5.RELEASE</version>
                </dependency>
            </dependencies>
        </plugin>
     </plugins>
   ~~~
此时要插件工作，不能简单的启动启动类，需要使用命令：spring-boot:run.  
注意：这种方式只能热部署java代码，但是对前端页面无能为力。  
      在此重启运行，会报端口抢占。因为热部署程序在系统后台以进程的形式进行。  
      需要打开任务管理器，关闭java.exe的进程，然后才可以重启。  

 7.1.2 SpringLoader方式二（在项目中使用jar包的形式）  
 将SpringLoader的jar放在项目的lib包下，方式一需要以命令方式启动，这里:  
 启动方式选择run configuration,设定启动参数vm arguments:
   ~~~
    -javaagent:\lib\springloaded-1.2.5.RELEASE.jar -noverify
   ~~~
这种再启动就不会像方式一存在端口抢占的问题。

7.2 DevTools  
SpringLoader与DevTools的区别：  
   SpringLoader 在部署项目时使用的是热部署的方式，DevTools采用的是重新部署的方式。  
导入依赖：  
   ~~~
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        &lt;!&ndash;其他项目依赖这个项目，不向下传递&ndash;&gt;
        <optional>true</optional>
    </dependency>
   ~~~
修改代码，可以看到控制台会自动重新启动，这就是在重新部署，  
和springloader的热部署就不同，因为重新部署所以对页面也是有用的。  

8.springboot整合spring data jpa.  
8.1 spring data JPA介绍  
    spring data就是spring提供的操作数据的框架。spring data JPA只是spring data框架下的基于JPA标准操作数据的模块，简化持久层的代码。  
    spring data jpa 底层其实还是hibernate。  

8.2 spring data jpa搭建  
    引入依赖，修改配置文件，StudentJpa.class使用@Table @Id等注解，建立映射。ApplicationTests.class测试类中测试。  

8.3 spring data jpa提供的核心接口  
Repository接口:    
    提供了基于方法名称命名的查询方式;提供了基于@Query注解查询与更新.  
    详情见:StudentJpaRepositoryByName.class与ApplicationTests.class.testJpaRepositoryByMethodName();  
    详情见:StudentJpaRepositoryQueryAnnotation.class与ApplicationTests.testQueryAnnotation();  
CrudRepository接口:   
     CrudRepository接口继承了Repository接口。
     详情见StudentJpaCrudRepository & ApplicationTests.testJpaCrudRepository();  
PagingAndSortingRepository接口:   
     该接口提供了分页与排序的操作。注意：该接口继承了CrudRepository接口。  
     可以利用Sort和Order排序、利用pageRequest分页，混搭来进行分页时排序。  
     详情见StudentJPagingAndSortingRepository.class & ApplicationTests.testPagingAndSortingRepository();   
JpaRepository接口:    
      该接口继承了PagingAndSortingRepository接口。开发最常用，间接继承的接口多。  
      详情见StudentJpaRepository & ApplicationTests.testJpaRepository();  
JPASpecificationExecutor接口:    
      该接口主要是提供了多条件查询的支持，并且可以在查询中添加分页与排序。  
      JPASpecificationExecutor是单独存在的，完全独立。所以一般利用接口多继承，也继承上面的其他接口；
      详情见StudentJPASpecificationExecutor & ApplicationTests.testStudentJPASpecificationExecutor();  
      
8.4 关联映射操作  
8.4.1 一对多的关联关系和级联操作    
      角色和用户：一个角色多个用户，一个用户只属于一个角色。  
      关系映射类详情见：UserMapping & RoleMapping。  
      映射关系操作见：UserMappingJpaRepository & ApplicationTests.testOneToManySave()和testOneToManyFind()一对多操作;  

8.4.2 多对多的关联关系和级联操作  
      角色和菜单：多对多  
      关系映射见：RoleMapping & MenusMapping  
      映射关系操作见：RoleMappingJpaRepository & ApplicationTests.testManyToManySave() testManyToManyFind()一对多操作;  
      注意：springboot data jpa底层hibernate是延迟加载(懒加载),级联查询时候修改fetch = Fetch.Eager理解加载。多对多关系建立依赖于第三张关系表。  
      
      
9.springboot整合ehcache【缓存技术】    
9.1 整合ehcache  
     添加坐标、  
     配置文件ehcache.xml,放在classpath下，即src/main/java/resource下。在ehcache的jar下复制过来，再改动。  
     在application.properties中配置ehcache的配置文件位置。  
     启动类添加注解开启ehcache。实现方法中添加@Cacheable注解，      
     详情见:ApplicationTests.testEhcache()  

9.2 @Ehcache的key属性  
    @Cacheable(name="指定配置文件的策略",key=“指定缓存的key")  
    key如果相同就使用缓存  
    详情见ApplicationTests.testEhcacheKeyField();   

9.3 @CacheEvict注解的使用  
    @CacheEvict(value = "defineBySelf",allEntries = true)  //清除以defineBySelf策略存储的缓存。  
    两次查询中添加一次，使用缓存，查询结果一致，需要再保存按钮中添加清除缓存。  
    详情见：ApplicationTests.testCacheEvict()以及StudentJpaEhcacheServiceImpl.saveStudent();  
    
10.springboot整合spring data redis【缓存技术】        
10.1 安装redis  
     安装编译器  yum install gcc-c++  
     解压redis  tar -zxvf redis-3.0.0.tar.gz  
     进入解压后目录编译  cd resis-3.0.0然后make  
     将redis安装到指定目录 make prefix=/usr/local/redis install  
     启动 cd /usr/local/redis    ./redis-server  
     停止   ctrl+c  
     这是前置启动，后置启动去解压缩包下cp命令拷贝一个redis.conf,将demonize的no改为yes,用./redis-server redis.conf启动  
     可以配合redis-desktop-manager可视化工具连接redis，查看redis数据库。  
 10.2 整合spring data redis步骤  
     spring data redis属于spring data下的一个模块，作用就是简化对于redis的操作。  
     A:修改pom,添加spring data redis坐标。    
     B:配置文件或者使用注解+代码配置redis。详情见:RedisConfig.java。  
10.3 spring data redis测试  
     详情见：ApplicationTests.testSpringDateRedis();  
     设置和获取redis键值对方法：redisTemplate.opsForValue().set("key","getech");  redisTemplate.opsForValue().get("key");  
10.4 提取redis的链接参数  
     redis配置类很多数据上线后需要修改，应提取出来放在配置文件中。  
     详情见：application.properties与RedisConfig。注意：@ConfigurationProperties(prefix = "")可以将配置文件中相同前缀的内容创建一个实体。  
10.5 存储对象  
     注意要重新设置序列化器，同时对象实体类要实现序列化接口。让对象可以被序列化成字节。  
     详情见：User &  ApplicationTests.testSetObject();  
10.6 以json格式存储java对象  
     上述以JDK序列化器存比json格式会浪费5倍以上空间。使用Jackson2JsonRedisSerializer序列化器替换JdkSerializationRedisSerializer。  
     详情见:ApplicationTests.testSetJsonObject();  
     
     
11 spring boot定时任务  
11.1 @Scheduled使用  
     scheduled是spring3.0以后自带的定时任务器。导入spring的jar就行。  
     使用@Scheduled标识定时任务执行的方法，cron表达式标识执行频率，@EnableScheduling在启动类启动。  
     详情见：ScheduledDemoController 与 Application。   
11.2 Quartz定时器  
     Quartz是job scheduling任务调度领域的开源项目，可以在javase和javaee使用。https://baike.baidu.com/item/quartz/3643055?fr=aladdin
11.2.1 Quartz的使用思路  
      job 任务   你要做什么事  
      Trigger 触发器  你什么时候去做  
      Scheduler 任务调度 你什么时候需要去做什么是？   
11.2.2 Quartz基本使用-javase整合         
      可以在普通javase项目使用。创建普通maven项目不用依赖springboot；  
      pom引入依赖GAV:org.quartz-scheduler  quartz  2.2.1   
      创建类QuartzDemo implements Job,实现execute方法，这个方法就是任务调度执行的方法。随意打印一句话，  
      测试类中main方法中:  
  ~~~ 
       public static void main(String[] args){
            JobDetail job = JobBuilder.newJob(QuartzDemo.class).build();
            /**
            **两种方法：
            ** 1.简单的trigger出发时间，通过Quartz提供的一个方法来完成简单的重复调用
            ** 2.cron Trigger 按照cron表达式来给定触发的时间
            **
            ***/
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repateSecondlyForever).build();
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * *  * ?")).build();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job,scheduler);
            scheduler.start();
       }
  ~~~ 
      启动main！   
11.2.3  springboot整合Quartz  
       导入jar：quartz、spring-context-support、spring-tx;   
       编写实现Job接口的定时执行任务；  
       编写配置类，配置Quartz，不需要像在javase中调用start()启动，而是springboot启动类中@EnableScheduling。  
11.2.4  Job类对象注入  
       直接autowired注入启动会报空指针异常，因为配置类中注入job，是通过反射。job不在spring ioc容器中，结果你在类里面注入ioc的对象，报错。  
       重写方法让job对象放到spring ioc容器中；  
       scheduler设置这个重写的类job工厂。  
       详情见：QuartzDemo、MyAdaptableJobFactory、QuartzConfig.schedulerFactoryBean();  
       
12. spring 项目集成hibernate、hibernate-jpa、spring data jpa  
12.1 spring整合hiberbate  
     12.1.1 导入jar  
     12.1.2 创建applicationContext.xml
   ~~~
    <!-- 配置读取properties文件的工具类 -->
    <context:property-placeholder  location =  "classpath:jdbc.properties"/>
    
    <!-- 配置c3p0数据库连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="driverClass" value="${jdbc.driver.class}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
    
    <!-- 配置Hibernate的sessionFactory -->
    <bean id="sessionFactory" calss="org.springframework.orm.hibernate5.localSessionFactory">
        <property name="dataSource" ref = "dataSource" />
    
        //hibernateProperties属性，配置与hibernate相关的内容，如显示slq语句，开启正向工程
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.hibernate.hbm2ddl.auto">update</prop>
            </props>
        </property>
    
        //扫描实体所在的包
        <property name="packageToScan">
                <list>
                    <value>com.bjsxt.pojo</value>          
                </list>
        </property>
    </bean>
    
    <!-- 配置Hibernate的事务管理器 -->
    <bean id="transcationManager" class="org.springframework.orm.hibernate5.HibernateTranscationManager">
        <property name="sessionFactory"  ref ="sessionFactory"/>
    </bean>
    
    <!-- 配置开启注解事务处理 -->
    <tx:annotaion-driver transaction-manager="transcationManager"/>
    
    <!-- 配置Spring Ioc的注解扫描 -->
    <context:component-scan base-package="com.bjsxt"/>

    <!-- 配置HibernateTemplate对象  避免在DaoImpl中extends HibernateDaoSupport -->
    <bean id="hibernateTemplate" class="org.springframework.orm.hibernate5.HibernateTemplate">
        <property name = "sessionFactory"  ref = "sessionFactory"/>   
    </bean>
   ~~~
 
   12.1.3 创建jdbc.properties
   ~~~
        jdbc.url=jdbc:mysql://;pca;jpst:3306/test
        jdbc.driver.class=com.mysql.jdbc.Driver
        jdbc.username=root
        jdbc.password=root
   ~~~
   
   12.1.4 编写实体类
   ~~~
    @Entity
    @Table(name="t_users")
    public class Users implements Serializable{
        @Id
        @GeneratedValue(Strategy=GenerationType.IDENTITY) //主键 自动递增
        @column(name = "userid")
        private Integer userid;
        
        @column(name = "username")
        private String username;
        
        @column(name = "userage")
        private Integer userage;
        
        //getter and setter
    }
   ~~~   
    
   12.1.5 编写UsersDao接口和实现类  
   ~~~
    public interface UsersDao{
        void insertUsers(Users users);
    }
    
    @Repository
    public class UsersDaoImpl  implements UsersDao{
        @Autowired
        private HibernateTemplate hibernateTemplate;
        
        @Override
        public void insertUsers(Users users){
            hibernateTemplate.save(users);
        }
    }
   ~~~   

   12.1.6 编写测试类
   ```
    @RunWith(SpringJunit4ClassRunner.calss)
    @ContextConfiguration("classpath:applicationContext.xml")
    public class UsersDapImplTest{
        @Autowired
        priavate UsersDao usersDao;
        
        @Test
        @Transcational
        @Rollback(false) //test类中默认自动回滚
        public void testInsertUsers{
            Users users = new Users("战三",20);//TODO 补充对应的构造方法。
            this.userDao.insertUsers(users);
        }
    }
   ```

   12.1.7 hibernate-hql查询  
   上述hibernateTemplate提供了主键查询，但是实际应用可能会出现其他条件。  
   hql:hibernate query language。    
   HQL的语法：将原来的sql语句的表和字段名称换成对象与属性名称。    
   ``` 
    List<Users> selectUsersByName(String name);
    
    @Override
    public List<Users> selectUsersByName(String name){
        //getCurrentSession:当前session必须要有事务边界，且只能处理唯一的一个事务，当事务提交或者回滚后session自动失效。
        //openSession：每次都会打开一个新的session，假如每次使用多次，则获得的是不同session对象，使用完毕后我们需要手动的调用close()关闭session。
        Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
        Query querey = session.creatQuery("from Users where username = :paramGet");//类名不是表名
        Query qwueryTemp = querey.setString("paramGet",name);
        return qwueryTemp.list();
    }
    //测试类中添加方法测试...
   ```

   12.1.8 hibernate-SQL查询  
   ~~~ 
    public List<Users> selectUsersByNameUseSql(String name){
        Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
        Query querey = session.creatSQLQuery("select * from t_users where username = ?");
        Query qwueryTemp = querey.addEntity(Users.class).setString(0,name);
        return qwueryTemp.list();
    }
   ~~~  

   12.1.9 hibernate-QBC查询  
   QBC将sql查询完全替代为对象和方法。  
   ~~~
    public List<Users> selectUsersByNameUseCriteria(String username){
        Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
        Criteria c = session.createCriteria(Users.class);
        c.add(Restrictions.eq("username",username));
        return c.list();
    }
   ~~~  

12.2 spring整合hibernate jpa  
  jpa由sun公司提供了一对对于持久层操作的标准（接口 + 文档）  
  hibernate是Gavin King开发的一套对于持久层操作的自动的orm框架。  
  hibernate jpa是由hibernate3.2版本提供了基于jpa的标准的实现，提供了一套按照jpa标准来实现持久层开发的api。  
12.2.1 项目创建：  
     在上面spring整合hibernate项目中导入jar : hibernate-entitymanager.jar  
  修改配置文件：  
   ~~~
        <!-- 去掉Hibernate的sessionFactory-->
        <!-- Spring整合JPA配置EntityManagerFactory-->
        <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
            <property name="dataSource" ref="dataSource"/>
            <property name="jpaVendorAdapter">
                    <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                        <!-- hibernate相关的属性的注入-->
                        <!-- 配置数据库类型 -->
                        <property name="database" value="MYSQL"/>
                        <!-- 正向工程自动创建表 -->
                        <property name="generateDdl" value="true"/>
                        <!-- 显示执行的SQL -->
                        <property name="showSql" value="true"/>
                    </bean>
            </property>
            <!--扫描实体的包-->
            <property name="packagesToScan">
                <list>
                    <value>com.bjsxt.pojo</value>
                </list>
            </property>
        </bean>
    
        <!-- 修改事务管理器 -->
        <bean id="transactionManager"  class="org.springframework.orm.jpa.JpaTransactionManager">
            <property name= "entityManagerFactory" ref= "entityManagerFactory"/>
        </bean>
   ~~~
     
12.2.2 hibernate-jpa的crud
   ~~~
    @Repository
    public class UsersDaoImpl  implements UsersDao{

        //@Autowired 不可使用应为在spring容器中直接没有
        //PersistenceContext(name="entityManagerFactory")
        private EntityManager entityManager;
        
        @Override
        public void insertUsers(Users users){
            this.entityManager.persist(users);
            //查询是find(),更新是merge(),删除着先查询再调用remove().
        }
    }
   ~~~   

12.2.3 hibernate-jpa的HQL语句  
   ~~~
    @Override
    public List<Users> selectUsersByName(String username){
       //参数用：参数名或者？都行 
       return this.entityManager.createQuery(" from Users where username = :abc").setParameter("abc",username).getResultList();
    }
   ~~~  

12.2.4 hibernate-jpa的SQL语句  
   ~~~
    @Override
    public List<Users> selectUsersByNameUseSQL(String username){
       //Hibernate Jpa中如果通过？方式来绑定参数，那么它的查数是从1开始的，而hibernate中是从0开始的。
       return this.entityManager.createNativeQuery("select * from t_users where username = ?",Users.class).setParameter(1,username).getResultList();
    }
   ~~~  

12.2.5 hibernate-jpa的QBC语句-criteria  
   ~~~
    public List<Users> selectUsersByNameUseCriteria(String username){
        //CriteriaBuilder对象，创建一个CriteriaQuery，创建查询条件。
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        //CriteriaQuery对象，执行查询的Criteria对象
        CriteriaQuery<Users> query = builder.createQuery(Users.class);
        //获取要查询的实体类的对象
        Root<Users> root = query.from(Users.class);
        //封装查询条件
        Predicate cate = builder.equals(root.get("username"),username);
        query.where(cate);
        //执行查询
        TypedQuery<Users> typeQuery = this.entityManager.createQuer(query);
        return typeQuery.getResultList();
    }
   ~~~  

12.3 Spring data jpa  
   spring data jpa是spring data项目下的一个模块，体用了一套基于jpa标准的操作数据库的简化方案，底层默认的是依赖hibernate jpa实现的。  
   技术特点：我们只需要定义接口并继承spring data jpa中所提供的接口就可以了，不需要编写接口实现类。  
12.3.1 spring整合spring data jpa项目搭建  
   复用spring集成hibernate-jpa的项目。添加额外的jar：spring data jpa、slf4j。  
   修改配置文件：    
       -applicationContext.xml头部中添加新的约束:spring-jpa.xsd;  
       -在后面添加  
   ~~~
       <!-- spring data jpa 的配置 -->
       <!-- base-package 扫描dao接口所在的包 -->
       <jpa: repositories base-package="com.bjsxt.dao"/>
   ~~~
   编写dao
   ```
       public interfacte UsersDao extends JpaRepository<Users,Integer>{ }
        //测试类中:直接调用userDao.save(users);
   ```
12.3.2  spring data jpa接口继承结构  
    上述搭建时继承了JpaRepository接口，接口下实现了很多基础方法,它在spring-data-jpa的jar下；   
    JpaRepository继承了PagingAndSortingRepository接口，它实现了分页和排序,它在spring-data-commons.jar;  
    PagingAndSortingRepository继承了CrudRepository接口，它实现了对数据库的crud；  
    CrudRepository接口继承了Repository接口，Repository接口只是一个标识接口。  
    JpaRepository-->PagingAndSortingRepository(分页排序)-->CrudRepository(crud)-->Repository(标识接口)  
    JpaRepository对从父接口返回值做了适配处理，比如将Iterable转化成List。  
    spring-data-jpa.jar下还有一个JpaSpecificationExecutor提供了一个自定义查询条件以及分页的接口。  
12.3.3 spring data jpa底层原理  
    上述编程直接调用userDao.save()方法，其方式是从父类接口中继承来的。在测试类中:  
    打印下注入的this.userDao对象：org.springframework.data.jpa.repository.support.SimpleJpaRepository@fba8bf.  
    打印注入的类型this.userDao.getClass()：class.com.sun.proxy.$Proxy29.可以看出代理对象是基于JDK的动态代理方法创建的。 
    查看源码SimpleJpaRepository实现了JpaRepository、JpaSpecificationExecutor，接口的实现类。可以自己实现：
   ```
    @Persistence(name="entityManagerFactory")
    private EntityManager em;
    @Test
    public void test(){
       JpaRepositoryFactory factory  = new JpaRepositoryFactory(em);
       //getRepository(UsersDao.class);可以帮助我们为接口生成实现类，而这个实现类是SimpleJpaRepository，
       //要求该接口必须要是继承Repository接口。
       UsersDao ud = factory.getRepository(UsersDao.class);
    }
   ```  
   注：在spring项目中配置文件指定了使用EntityManager替换sessionFactory，见12.2.1，springboot项目不做配置。  
        但是springboot默认就是引入的entityManager，验证参看ApplicationTests.testEntityManagerSourceFrom();  
12.3.4 Repository接口-方法命名规则查询     
    Repository接口是spring data jpa中为我们提供的所有接口中的顶层接口，  
    Repository提供了两种查询方式的支持：1）基于方法名称命令规则查询 2）基于@Query注解查询。   
    同springboot集成spring data jpa【8.3章节】。StudentJpaRepositoryByName.class。   
    规则：findBy（关键字） + 属性名称（属性名称的首字母大写）+ 查询条件（首字母大写,默认相等 Is、Equal、Or...);   
    如：findByUsername();findByUsernameIs,findByUsernameLike;findByUsernameAndAge;findByUsernameAndAgeGreaterThanEqual.   
12.3.5 Repository接口@Query查询JPQL语句   
    在dao接口中直接使用@Query(@value="hql语句"),@value可以不要，直接hql语句(类名替代表名)  
    spring中集成spring data jpa和springboot中集成在使用中是一样的,参看StudentJpaRepositoryQueryAnnotation.class.queryByNameUseHQL();     
12.3.6 Repository接口@Query查询SQL语句  
    JPA默认是使用HQL，使用sql查询时候需要nativeQuery属性转化成使用sql，    
    同springboot集成spring data jpa，见StudentJpaRepositoryQueryAnnotation.class.queryByNameUseSql();   
12.3.7 Repository接口@Query更新操作  
    同springboot集成spring data jpa，见StudentJpaRepositoryQueryAnnotation.class.updateUsersNameById();   
    使用@Modifying注解，在@Test方法中，更新需要加事物注解，测试方法中会默认回滚，为了观测，还需要加@rollback注解，修改为false。   
12.3.8 CrudRepository接口的使用    
    dao接口extends CrudRepository<StudentJpa,Integer>;  
    @Test中直接调用dao的save方法保存，此时不用添加事务@Transcational注解，因为CrudRepository的save实现接口已经添加了。   
    详情见ApplicationTests.testJpaCrudRepository();  
    hibernate对象的游离、临时、持久化在session关闭时对象持久化自动保存到数据库，详情见ApplicationTests.testJpaCrudRepository02(); 
12.3.9 PagingAndSortingRepository接口-分页处理   
    接口继承PagingAndSortingRepository，提供了Iterable<T> findAll(Sort var1)排序接口与Page<T> findAll(Pageable var1)分页接口；  
    分页：    
   ```
    Pageable pageable = new PageRequest(0, 2);
        Page<StudentJpa> all = this.studentJPagingAndSortingRepository.findAll(pageable);
        System.out.println("总条数:" + all.getTotalElements()  + ",总页数： " + all.getTotalPages());
        List<StudentJpa> content = all.getContent();
        for(StudentJpa s : content){
        System.out.println("当前页数据 : " + s);
    }
   ```   
   详情见ApplicationTests.testPagingAndSortingRepository();   
12.3.10 PagingAndSortingRepository接口-排序处理   
     排序可以多列也可以单列排序，利用sort对象的；
   ```
     //单列
     Sort singleSort = new Sort(Direction.DESC,"age");  
     //多列
     Order orderMul1 = new Order(Direction.DESC,"id");
     Order orderMul2 = new Order(Direction.DESC,"age");
     Sort sort = new Sort(orderMul1,orderMul2);
   ```   
   详情见ApplicationTests.testPagingAndSortingRepository();      
12.3.11 JpaRepository接口的使用   
     JpaRepository接口时我们开发时使用最多的接口，其特点时可以帮助我们将其他接口的方法的返回值做适配处理，可以使得我们开发时更方便的使用这些方法。  
   ```
      List<StudentJpa> all = studentJpaRepository.findAll();
   ```  
   详情见ApplicationTests.testJpaRepository();   
12.3.12 JpaSpecificationExecutor接口-单条件查询   
     完成多条件查询，并且支持分页与排序。  
     接口类继承（Java中类单继承，接口多继承）JpaRepository<StudentJpa,Integer>, JpaSpecificationExecutor<StudentJpa>，见StudentJPASpecificationExecutor类，  
     JpaSpecificationExecutor不能单独使用，需要配合jpa中其他接口一起使用，因为它没有继承任何jpa接口，但是spring data jpa的原理就是通过继承Repository的接口生成接口的代理对象。  
     测试类见：ApplicationTests.testStudentJPASpecificationExecutor()的第一种单条件测试。  
12.3.13 JpaSpecificationExecutor接口-多条件查询-方式一    
     用List<Predicate>来封装查询条件，用criteriaBuilder.and(list.toArray(arr))来指定多条件的组合方式为and。  
     详情见：ApplicationTests.testStudentJPASpecificationExecutor()多条件查询方式一。   
12.3.14 JpaSpecificationExecutor接口-多条件查询-方式二    
     条件构建器CriteriaBuilder直接链式拼接查询条件， criteriaBuilder.and(criteriaBuilder.equal(root.get("name"),"张三"),criteriaBuilder.equal(root.get("age"),11));   
     详情见：ApplicationTests.testStudentJPASpecificationExecutor()多条件查询（其他方式）方式二。  
12.3.15 JpaSpecificationExecutor接口-多条件查询-分页处理   
     findAll()根据提示可以传条件和分页参数，分别拼接查询条件和分页参数即可；  
     参看ApplicationTests.testSpecificationPage().  
12.3.16 JpaSpecificationExecutor接口-多条件查询-排序处理   
     findAll()可以传入查询条件和排序规则；  
     参看ApplicationTests.testSpecificationOrderBy().    
12.3.17 JpaSpecificationExecutor接口-多条件查询-分页+排序    
     分页对象中，除了传入当前页，和每页显示数量，还有一个构造方法，直接还可以传入排序规则。    
     参看ApplicationTests.testSpecificationPageOrderBy().    
12.3.18 自定义Repository接口     
     首先自定义接口：StudentJpaDefineRepository；    
     然后使用接口，继承接口：extends JpaRepository<StudentJpaDefineDao,Integer>, JpaSpecificationExecutor<StudentJpa>,StudentJpaDefineRepository；  
     再然后实现接口，详情见StudentJpaDefineDaoImpl类；  
     测试类见：ApplicationTests.testDefineRepository().   
12.3.19 创建一对一关联关系    
     一对一的关系,只需要有一方存在这边的一个外键。
   ~~~
   @Entity
   @Table(name = "role_mapping")
   public class RoleMapping {
        @Id //主键
        @GeneratedValue(strategy = GenerationType.IDENTITY) //自增策略
        @Column(name = "role_id")
        private Integer roleId;
    
        @Column(name = "role_name")
        private String roleName;
    
        //告诉有关联，匹配到对方的一个属性就好。
        @OneToOne(mappedBy = "roleMapping")
        private UserMapping userMapping;
   }
    
    @Entity
    @Table(name = "user_mapping")
    public class UserMapping {
        @Id //主键
        @GeneratedValue(strategy = GenerationType.IDENTITY) //自增策略
        @Column(name = "user_id")
        private Integer userId;
    
        @Column(name = "name")
        private String name;
    
        @Column(name = "age")
        private Integer age;
    
        @Column(name = "address")
        private String address;

        @OneToOne
        @JoinColumn(name = "role_id")    //维护一个外键
        private RoleMapping roleMapping; 
    }    
   ~~~~

12.3.20 操作一对一关联关系   
    假设用户和角色一对一,
   ```
    //角色类中
    @OneToOne(mappedBy("roles"))
    private Users user;
    
    //用户类中
    @OneToOne
    @JoinColumn(name="roles_id") //维护一个外键
    private Roles roles;
    
    //测试类中就各自new对象，然后user.setRoles(),role.setUsers()维护关系。     
   ```
12.3.21 创建一对多关联关系   
    假设一个用户一个角色，一个角色有多个用户   
   ~~~
    //用户类
    @ManyToOne()
    @JoinColumn(name = "roles_id")
    private Role roles;
    
    //角色类
    @OneToMany(mappedBy = "roles")
    private Set<Users> users = new HashSet<>();
   ~~~
12.3.22 操作一对多关联关系   
   保存和查询分别参看：ApplicationTests的testOneToManySave() & testOneToManyFind()；   
   保存的级联操作需要在用户类中的：  
   ~~~
     @ManyToOne后添加(cascade = CascadeType.PERSIST
   ~~~
   观察控制台执行查看的SQL可知，级联查询，spring data jpa使用的是left join操作，直接两张表一起查询了。   
12.3.23 创建多对多关联关系   

12.3.24 操作多对多关联关系   
   