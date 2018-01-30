package com.tos.applock.domain;

/**
 * Created by ALIENWARE on 2017/3/15.
 */

public class EventAppInfo{

    private Integer flag;

    private String app_name;

    public EventAppInfo(){};

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }
}
