package com.tos.pojo;

import java.io.Serializable;

/**
 * app安装包分类表
 * Created by hasaa on 2016/3/14.
 */
public class App_package_type implements Serializable {

    private String id;
    private String name;

    public App_package_type(){};

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

    @Override
    public String toString() {
        return "App_package_type{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
