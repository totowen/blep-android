package com.tos.Dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Description: app实例运行记录Dto
 * @author weishengbin
 *
 */

public class AppRunRecordDto implements Serializable {
	private static final long serialVersionUID = -5959323167928925216L;
	
	private String id;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 退出时间
	 */
	private Date exitTime;
	
	/**
	 * 配置在哪个app上
	 */
	private String appInstance;
	
	/**
	 * 配置在哪个app-id
	 */
	private String appInstanceId;
	
	/**
	 * 最后一次运行时长
	 */
	private String runTime;
	
	/**
	 * 总运行次数
	 */
	private Integer allRunNumber;
	
	/**
	 * 总运行时长
	 */
	private long allRunDuration;
	
	/**
	 * 展示图片(附件)
	 */
	private List<String> attachmentIds;
	
	/**
	 * 图标(附件)
	 */
	private String attachmentId;
	
	/**
	 * 包名
	 */
	private String appInstancePackageName;

	/**
	 * app状态（开启还是关闭）
	 */
	private String appStatus;

	/**
	 * app状态（开启还是关闭）(针对pad端)
	 */
	private String padAppStatus;

	/**
	 * app开启的时间段
	 */
	/**
	 * 开始时间
	 */
	private String statusStartTime;

	/**
	 * 结束时间
	 */
	private String statusEndTime;


	public String getAppInstancePackageName() {
		return appInstancePackageName;
	}

	public void setAppInstancePackageName(String appInstancePackageName) {
		this.appInstancePackageName = appInstancePackageName;
	}

	@Override
	public String toString() {
		return "AppRunRecordDto{" +
				"id='" + id + '\'' +
				", startTime=" + startTime +
				", exitTime=" + exitTime +
				", appInstance='" + appInstance + '\'' +
				", appInstanceId='" + appInstanceId + '\'' +
				", runTime='" + runTime + '\'' +
				", allRunNumber=" + allRunNumber +
				", allRunDuration=" + allRunDuration +
				", attachmentIds=" + attachmentIds +
				", attachmentId='" + attachmentId + '\'' +
				", appInstancePackageName='" + appInstancePackageName + '\'' +
				", appStatus='" + appStatus + '\'' +
				", padAppStatus='" + padAppStatus + '\'' +
				", statusStartTime='" + statusStartTime + '\'' +
				", statusEndTime='" + statusEndTime + '\'' +
				'}';
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

	public Integer getAllRunNumber() {
		return allRunNumber;
	}

	public void setAllRunNumber(Integer allRunNumber) {
		this.allRunNumber = allRunNumber;
	}

	public long getAllRunDuration() {
		return allRunDuration;
	}

	public void setAllRunDuration(long allRunDuration) {
		this.allRunDuration = allRunDuration;
	}

	public String getAppInstanceId() {
		return appInstanceId;
	}

	public void setAppInstanceId(String appInstanceId) {
		this.appInstanceId = appInstanceId;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getAppInstance() {
		return appInstance;
	}

	public void setAppInstance(String appInstance) {
		this.appInstance = appInstance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getPadAppStatus() {
		return padAppStatus;
	}

	public void setPadAppStatus(String padAppStatus) {
		this.padAppStatus = padAppStatus;
	}

	public String getStatusStartTime() {
		return statusStartTime;
	}

	public void setStatusStartTime(String statusStartTime) {
		this.statusStartTime = statusStartTime;
	}

	public String getStatusEndTime() {
		return statusEndTime;
	}

	public void setStatusEndTime(String statusEndTime) {
		this.statusEndTime = statusEndTime;
	}
}
