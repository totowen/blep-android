package com.tos.pojo;

import java.io.Serializable;

/**
 * Pad表
 * Created by hasaa on 2016/3/14.
 */
public class Pad implements Serializable {
    private String id;
    private String name;//名称
    private String serial_number;//序列号
    private String create_time;//创建时间
    private String owner;//所属用户

    public Pad() {};

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

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Pad{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", serial_number='" + serial_number + '\'' +
                ", create_time='" + create_time + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
