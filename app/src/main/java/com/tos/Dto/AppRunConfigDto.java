package com.tos.Dto;

import java.io.Serializable;

/**
 * 
 * @Description: app实例运行期配置Dto
 * @author weishengbin
 *
 */
public class AppRunConfigDto implements Serializable {
	private static final long serialVersionUID = -8541830380781818766L;
	
	private String id;
	
	/**
	 * 允许还是禁止
	 */
	private String type;
	
	/**
	 * 如周一，周二
	 */
	private String days;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;

	@Override
	public String toString() {
		return "AppRunConfigDto [id=" + id + ", type=" + type + ", days=" + days + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

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

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
