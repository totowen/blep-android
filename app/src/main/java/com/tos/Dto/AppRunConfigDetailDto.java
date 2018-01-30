package com.tos.Dto;

import java.io.Serializable;
import java.util.List;

/**
 * 返回给前端app设置的时间段Dto
 *
 * @author weishengbin
 */

public class AppRunConfigDetailDto implements Serializable {
	private static final long serialVersionUID = 9009252631219648971L;
	/**周几
	 */
	private String day;
	/**时间段
	 */
	private List<String> times;

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

	@Override
	public String toString() {
		return "AppRunConfigDetailDto{" +
				"day='" + day + '\'' +
				", times=" + times +
				", appStatus='" + appStatus + '\'' +
				", padAppStatus='" + padAppStatus + '\'' +
				", statusStartTime='" + statusStartTime + '\'' +
				", statusEndTime='" + statusEndTime + '\'' +
				'}';
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<String> getTimes() {
		return times;
	}

	public void setTimes(List<String> times) {
		this.times = times;
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
