package com.learn.springboot.pojo;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单实体
 *
 * 菜单 vs  角色    多对过
 */
@Entity
@Table(name = "menus_mapping")
public class MenusMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menus_id")
    private Integer menusId;

    @Column(name = "menus_name")
    private String menusName;

    @Column(name = "menus_url")
    private String menusUrl;

    @Column(name = "parent_id")
    private Integer parentId;

    //mappedBy配置在另外多的一方的对象实例化名称
    @ManyToMany(mappedBy = "menus")
    private Set<RoleMapping> roles = new HashSet<>();


    public Set<RoleMapping> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleMapping> roles) {
        this.roles = roles;
    }

    public Integer getMenusId() {
        return menusId;
    }

    public void setMenusId(Integer menusId) {
        this.menusId = menusId;
    }

    public String getMenusName() {
        return menusName;
    }

    public void setMenusName(String menusName) {
        this.menusName = menusName;
    }

    public String getMenusUrl() {
        return menusUrl;
    }

    public void setMenusUrl(String menusUrl) {
        this.menusUrl = menusUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "MenusMapping{" +
                "menusId=" + menusId +
                ", menusName='" + menusName + '\'' +
                ", menusUrl='" + menusUrl + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}