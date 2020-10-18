package com.learn.view.pojo;

/***
 * @ClassName User
 * @Description TODO
 * @Author Taycen
 * @Date 2019/8/22 0022 16:48
 * @Jdk 1.8
 ***/
public class User {
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
}
