package com.tos.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasaa on 2016/3/14.
 */
public class App_package implements Parcelable {
    private String id;
    private String name;//名称
    private String developer;//开发方
    private String description;//app描述
    private String create_time;//创建时间
    private String star;//星级
    private String type;//所属类型
    private String level;//所属级别
    private List<String> attachmentIds = new ArrayList<String>();//展示图片(附件)
    private String attachmentId;//图标(附件)
    private String attachmentApkId;//apk链接
    private String appPackageName;//包名

    public App_package(){};

    protected App_package(Parcel in) {
        id = in.readString();
        name = in.readString();
        developer = in.readString();
        description = in.readString();
        create_time = in.readString();
        star = in.readString();
        type = in.readString();
        level = in.readString();
        attachmentIds = in.readArrayList(String.class.getClassLoader());
        attachmentId = in.readString();
        attachmentApkId = in.readString();
        appPackageName  = in.readString();
    }

    public static final Creator<App_package> CREATOR = new Creator<App_package>() {
        @Override
        public App_package createFromParcel(Parcel in) {
            App_package app_package = new App_package();
            app_package.setId(in.readString());
            app_package.setName(in.readString());
            app_package.setDeveloper(in.readString());
            app_package.setDescription(in.readString());
            app_package.setCreate_time(in.readString());
            app_package.setStar(in.readString());
            app_package.setType(in.readString());
            app_package.setLevel(in.readString());
            app_package.setAttachmentIds(in.readArrayList(String.class.getClassLoader()));
            app_package.setAttachmentId(in.readString());
            app_package.setAttachmentApkId(in.readString());
            app_package.setAppPackageName(in.readString());
            return app_package;
        }

        @Override
        public App_package[] newArray(int size) {
            return new App_package[0];
        }
    };

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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<String> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentApkId() {
        return attachmentApkId;
    }

    public void setAttachmentApkId(String attachmentApkId) {
        this.attachmentApkId = attachmentApkId;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "App_package{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", developer='" + developer + '\'' +
                ", description='" + description + '\'' +
                ", create_time='" + create_time + '\'' +
                ", star='" + star + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", attachmentIds=" + attachmentIds +
                ", attachmentId='" + attachmentId + '\'' +
                ", attachmentApkId='" + attachmentApkId + '\'' +
                ", appPackageName='" + appPackageName + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(developer);
        dest.writeString(description);
        dest.writeString(create_time);
        dest.writeString(star);
        dest.writeString(type);
        dest.writeString(level);
        dest.writeList(attachmentIds);
        dest.writeString(attachmentId);
        dest.writeString(attachmentApkId);
        dest.writeString(appPackageName);
    }
}
