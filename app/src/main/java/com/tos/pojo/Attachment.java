package com.tos.pojo;

import java.io.Serializable;

/**
 * 附件表
 * Created by hasaa on 2016/3/14.
 */
public class Attachment implements Serializable {
    private String id;
    private String type;//类型
    private String path;//路径
    private String create_time;//创建时间
    private String description;//描述
    private String title;//标题
    private String flowFilename;    //原文件名
    private String flowCurrentChunkSize;//文件大小
    private String contentType;    //传输的文件类型
    private String suffix;//文件后缀名
    private String owner;//所属owner

    public Attachment(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlowFilename() {
        return flowFilename;
    }

    public void setFlowFilename(String flowFilename) {
        this.flowFilename = flowFilename;
    }

    public String getFlowCurrentChunkSize() {
        return flowCurrentChunkSize;
    }

    public void setFlowCurrentChunkSize(String flowCurrentChunkSize) {
        this.flowCurrentChunkSize = flowCurrentChunkSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", create_time='" + create_time + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", flowFilename='" + flowFilename + '\'' +
                ", flowCurrentChunkSize='" + flowCurrentChunkSize + '\'' +
                ", contentType='" + contentType + '\'' +
                ", suffix='" + suffix + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
