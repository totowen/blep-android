package com.tos.pojo;

import java.io.Serializable;

/**
 * 用户表
 * Created by hasaa on 2016/3/14.
 */
public class User implements Serializable {
    private String id;
    private String name;//登录名
    private String password;//登录密码
    private String role;    //角色
    private String create_time;    //创建时间
    private String last_login_time;//最后登录时间

    public User() {};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", create_time='" + create_time + '\'' +
                ", last_login_time='" + last_login_time + '\'' +
                '}';
    }


}
