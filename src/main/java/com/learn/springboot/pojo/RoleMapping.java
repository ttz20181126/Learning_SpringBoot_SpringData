package com.learn.springboot.pojo;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * RoleMapping 角色
 * UserMapping 用户
 * 映射一对多
 * */
@Entity
@Table(name = "role_mapping")
public class RoleMapping {


    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增策略
    @Column(name = "role_id")
    private Integer roleId;


    @Column(name = "role_name")
    private String roleName;


    @OneToMany(mappedBy = "roleMapping")
    private Set<UserMapping> users = new HashSet<>();

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}