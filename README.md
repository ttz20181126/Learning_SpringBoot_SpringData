# springBoot_start_with_lesson
springboot入门教程

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
Repository接口  
    提供了基于方法名称命名的查询方式;提供了基于@Query注解查询与更新.  
    详情见:StudentJpaRepositoryByName.class与ApplicationTests.class.testJpaRepositoryByMethodName();  
    详情见:StudentJpaRepositoryQueryAnnotation与ApplicationTests.testQueryAnnotation();  
CrudRepository接口  
     CrudRepository接口继承了Repository接口。
     详情见StudentJpaCrudRepository & ApplicationTests.testJpaCrudRepository();  
PagingAndSortingRepository接口  
     该接口提供了分页与排序的操作。注意：该接口继承了CrudRepository接口。  
     可以利用Sort和Order排序、利用pageRequest分页，混搭来进行分页时排序。  
     详情见StudentJPagingAndSortingRepository.class & ApplicationTests.testPagingAndSortingRepository();   
JpaRepository接口  
      该接口继承了PagingAndSortingRepository接口。开发最常用，间接继承的接口多。  
      详情见StudentJpaRepository & ApplicationTests.testJpaRepository();
JPASpecificationExecutor接口  
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
     
     
    
     
           
   
    