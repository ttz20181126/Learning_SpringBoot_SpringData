package com.learn.springboot.pojo;

import java.io.Serializable;

/***
 * @ClassName User  spring boot整合 jsp、freemarker、thymeleaf。
 * @Description TODO
 * @Author Taycen
 * @Date 2019/8/22 0022 16:48
 * @Jdk 1.8
 ***/
public class User implements Serializable {
    private static final long serialVersionUID = -5736834809501397621L;

    private Integer userId;
    private Integer userAge;
    private String userName;

    public User() {
    }

    public User(Integer userId, Integer userAge, String userName) {
        this.userId = userId;
        this.userAge = userAge;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userAge=" + userAge +
                ", userName='" + userName + '\'' +
                '}';
    }
}
