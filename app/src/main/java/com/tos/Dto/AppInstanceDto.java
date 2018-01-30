package com.tos.Dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description: app实例Dto
 * @author weishengbin
 *
 */
public class AppInstanceDto implements Serializable {
	private static final long serialVersionUID = 5184919341249139098L;
	
	private String id;
	
	/**
	 * 创建时间
	 */
	private Date installTime;
	
	/**
	 * 对应pad mac
	 */
	private String mac;
	
	/**
	 * 包名
	 */
	private String appPackageName;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAppPackageName() {
		return appPackageName;
	}

	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}
}
