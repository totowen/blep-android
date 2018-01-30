package com.tos.Dto;

import java.io.Serializable;

/**
 * 
 * @description 服务BD Dto
 * @author weishengbin
 * @date 2016年1月4日
 */
public class ColleagueDto implements Serializable {
    private static final long serialVersionUID = -8845383019890974212L;

    private String id;

    private String realName;

    private String name;

    private String password;

    private String phone;

    private String email;
    
    private String roleName;
    /**
     * user类型，说明是公司，企业HR，还是猎头，还是人才
     */
    private String userType;
    /**
	 * 邮箱密码
	 */
	private String emailPassword;
	private String department;
	private String code;

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
    
    
}
