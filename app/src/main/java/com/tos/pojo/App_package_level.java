package com.tos.pojo;

import java.io.Serializable;

/**
 * app安装包级别
 * Created by hasaa on 2016/3/14.
 */
public class App_package_level implements Serializable {
    private String id;
    private String name;

    public App_package_level(){};

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
        return "App_package_level{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
