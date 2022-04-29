package com.learn.springboot.pojo;


import org.apache.ibatis.annotations.One;

import javax.persistence.*;

/**
 * RoleMapping 角色
 * UserMapping 用户
 * 映射一对多
 * */
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

    //@OneToOne
    //@JoinColumn(name = "role_id") //维护一个外键盘
    //private RoleMapping roleMapping;

    @ManyToOne(cascade = CascadeType.PERSIST)   //级联操作报错用户的时候保存角色。
    @JoinColumn(name = "role_id")  //维护外键
    private RoleMapping roleMapping;

    public RoleMapping getRoleMapping() {
        return roleMapping;
    }

    public void setRoleMapping(RoleMapping roleMapping) {
        this.roleMapping = roleMapping;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserMapping{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address;
    }
}