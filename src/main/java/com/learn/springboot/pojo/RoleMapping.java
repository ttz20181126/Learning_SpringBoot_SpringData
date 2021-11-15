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

    /**
     * JoinTable 关联表。多对多通过中间表。
     */
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name="t_role_menus",joinColumns = @JoinColumn(name="role_id"),inverseJoinColumns = @JoinColumn(name = "menus_id"))
    private Set<MenusMapping> menus = new HashSet<>();

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


    public Set<UserMapping> getUsers() {
        return users;
    }

    public void setUsers(Set<UserMapping> users) {
        this.users = users;
    }


    public Set<MenusMapping> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenusMapping> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "RoleMapping{" +
                "roleId=" + roleId +
                ", roleName='" + roleName;
    }
}