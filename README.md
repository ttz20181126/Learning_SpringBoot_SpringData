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
~~~
     
6.3 @ControllerAdvice + @ExceptionHandle注解处理异常  
但是6.2的处理只针对当前controller的异常类有用。另外一个controller如果空指针异常仍然跳转到error.html，不是error_null.html.  






6.4 配置SimpleMappingExceptionResolver处理异常
6.5 自定义HandlerExceptionResolver。

