package com.learn.springboot.pojo;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
** springboot 数据校验
 */
public class Student {

    private Integer id;

    //非空校验。使用了hibernate-validator框架。
    @NotBlank(message = "用户名不能为空（默认值是：不能为空）")
    @Length(min=2,max = 6,message = "最小长度为2,最大长度为6")
    private String name;

    @NotNull
    @Min(value = 12)
    private Integer age;

    //@Email(regexp = "")
    //private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Student(){}

    public Student(Integer id, @NotBlank(message = "用户名不能为空（默认值是：不能为空）") @Length(min = 2, max = 6, message = "最小长度为2,最大长度为6") String name, @NotNull @Min(value = 12) Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(@NotBlank(message = "用户名不能为空（默认值是：不能为空）") @Length(min = 2, max = 6, message = "最小长度为2,最大长度为6") String name, @NotNull @Min(value = 12) Integer age) {
        this.name = name;
        this.age = age;
    }
}