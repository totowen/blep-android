package com.tos.Dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppRunConfigAndRecordDto implements Serializable {
	private static final long serialVersionUID = -7414251104219256920L;

	private String id;
	
	/**返回pad端的时间段设置Dto
	 */
	private List<AppRunConfigDetailDto> appRunConfigDetailDtos;
	
	/**对应的包名
	 */
	private String appPackageName;
	
	/**返回手机端的时间段设置Dto
	 */
	private List<AppRunConfigDto> appRunConfigDtos;
	
	/**
	 * 最后一次运行时间
	 */
	private Date lastRunTime;
	
	/**
	 * 最后一次运行时长
	 */
	private long lastRunDuration;
	
	/**
	 * 次数&时长
	 */
	private AppRunStatisticalDto appRunStatisticalDto;

	@Override
	public String toString() {
		return "AppRunConfigAndRecordDto{" +
				"id='" + id + '\'' +
				", appRunConfigDetailDtos=" + appRunConfigDetailDtos +
				", appPackageName='" + appPackageName + '\'' +
				", appRunConfigDtos=" + appRunConfigDtos +
				", lastRunTime=" + lastRunTime +
				", lastRunDuration=" + lastRunDuration +
				", appRunStatisticalDto=" + appRunStatisticalDto +
				'}';
	}

	/**
	 * 对应的统计记录
	 */

	public String getAppPackageName() {
		return appPackageName;
	}

	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}

	public List<AppRunConfigDto> getAppRunConfigDtos() {
		return appRunConfigDtos;
	}

	public void setAppRunConfigDtos(List<AppRunConfigDto> appRunConfigDtos) {
		this.appRunConfigDtos = appRunConfigDtos;
	}

	public List<AppRunConfigDetailDto> getAppRunConfigDetailDtos() {
		return appRunConfigDetailDtos;
	}

	public void setAppRunConfigDetailDtos(List<AppRunConfigDetailDto> appRunConfigDetailDtos) {
		this.appRunConfigDetailDtos = appRunConfigDetailDtos;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public long getLastRunDuration() {
		return lastRunDuration;
	}

	public void setLastRunDuration(long lastRunDuration) {
		this.lastRunDuration = lastRunDuration;
	}

	public AppRunStatisticalDto getAppRunStatisticalDto() {
		return appRunStatisticalDto;
	}

	public void setAppRunStatisticalDto(AppRunStatisticalDto appRunStatisticalDto) {
		this.appRunStatisticalDto = appRunStatisticalDto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
