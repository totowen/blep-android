package com.tos.pojo;

import java.io.Serializable;

/**
 * Created by hasaa on 2016/3/14.
 */
public class App_run_record implements Serializable {
    private String id;
    private String startTime;
    private String exitTime;
    private String appInstance;

    public App_run_record(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getAppInstance() {
        return appInstance;
    }

    public void setAppInstance(String appInstance) {
        this.appInstance = appInstance;
    }

    @Override
    public String toString() {
        return "App_run_record{" +
                "id='" + id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", exitTime='" + exitTime + '\'' +
                ", appInstance='" + appInstance + '\'' +
                '}';
    }
}
