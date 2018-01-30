package com.tos.Dto;

import java.io.Serializable;

/**
 * 
 * @Description: app安装包分类表Dto
 * @author weishengbin
 *
 */
public class AppPackageTypeDto implements Serializable {
	private static final long serialVersionUID = 2521857791568412393L;
	
	private String id;
	
	/**
	 * 名称
	 */
	private String name;

	@Override
	public String toString() {
		return "AppPackageTypeDto [id=" + id + ", name=" + name + "]";
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
}
