package com.tos.Dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Description: app安装包Dto
 * @author weishengbin
 *
 */
public class AppPackageDto implements Serializable {
	private static final long serialVersionUID = 6434472296478040038L;

	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 开发方
	 */
	private String developer;

	/**
	 * app描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 星级
	 */
	private String star;

	/**
	 * 所属类型
	 */
	private AppPackageTypeDto typeDto;

	/**
	 * 所属级别
	 */
	private AppPackageLevelDto levelDto;

	/**
	 * 展示图片(附件)
	 */
	private List<String> attachmentIds;

	/**
	 * 图标(附件)
	 */
	private String attachmentId;

	/**
	 * apk文件(附件)
	 */
	private String attachmentApkId;

	/**
	 * 包名(com.xxx.xxx)
	 */
	private String appPackageName;

	/**
	 * 状态
	 */
	private String status;


	@Override
	public String toString() {
		return "AppPackageDto{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", developer='" + developer + '\'' +
				", description='" + description + '\'' +
				", createTime=" + createTime +
				", star='" + star + '\'' +
				", typeDto=" + typeDto +
				", levelDto=" + levelDto +
				", attachmentIds=" + attachmentIds +
				", attachmentId='" + attachmentId + '\'' +
				", attachmentApkId='" + attachmentApkId + '\'' +
				", appPackageName='" + appPackageName + '\'' +
				'}';
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

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public List<String> getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(List<String> attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	public AppPackageTypeDto getTypeDto() {
		return typeDto;
	}

	public void setTypeDto(AppPackageTypeDto typeDto) {
		this.typeDto = typeDto;
	}

	public AppPackageLevelDto getLevelDto() {
		return levelDto;
	}

	public void setLevelDto(AppPackageLevelDto levelDto) {
		this.levelDto = levelDto;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
