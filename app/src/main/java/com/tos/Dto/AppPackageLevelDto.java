package com.tos.Dto;

import java.io.Serializable;

/**
 * 
 * @Description: app安装包级别Dto
 * @author weishengbin
 *
 */
public class AppPackageLevelDto implements Serializable {
	private static final long serialVersionUID = 8720989342531561255L;
	
	private String id;
	
	/**
	 * 名称
	 */
	private String name;

	@Override
	public String toString() {
		return "AppPackageLevelDto [id=" + id + ", name=" + name + "]";
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
