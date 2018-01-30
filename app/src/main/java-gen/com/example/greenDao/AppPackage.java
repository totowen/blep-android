package com.example.greenDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table APP_PACKAGE.
 */
public class AppPackage {

    private Long id;
    private String name;
    private String developer;
    private String description;
    private java.util.Date createTime;
    private String star;
    private String typeDto;
    private String levelDto;
    private String appPackageName;

    public AppPackage() {
    }

    public AppPackage(Long id) {
        this.id = id;
    }

    public AppPackage(Long id, String name, String developer, String description, java.util.Date createTime, String star, String typeDto, String levelDto, String appPackageName) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.description = description;
        this.createTime = createTime;
        this.star = star;
        this.typeDto = typeDto;
        this.levelDto = levelDto;
        this.appPackageName = appPackageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTypeDto() {
        return typeDto;
    }

    public void setTypeDto(String typeDto) {
        this.typeDto = typeDto;
    }

    public String getLevelDto() {
        return levelDto;
    }

    public void setLevelDto(String levelDto) {
        this.levelDto = levelDto;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

}
