package com.hl.web.form;


import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hl.common.model.BaseBean;


public class UserForm extends BaseBean {

	@NotEmpty
	@Length(min=2,max=32)
	private String userName;
	
	//初始化密码为默认密码
//	@NotEmpty
//	@Length(min=0,max=32)
//	private String password;
	
	/**
	 * 姓名
	 */
	@Length(min=0,max=16)
	private String fullName;
	
	/**
	 * 性别
	 */
	private int gender;
	
	/**
	 * 手机号
	 */
	private String cellphone;

	@Length(min=0,max=64)
	private String address;
	
	@Length(min=0,max=64)
	private String email;
	
	private Date addTime;
	
	private String lastLoginTime;
	
	private int loginCount;
	
	@Length(min=0,max=128)
	private String note;
	
	private long status;

	private String[] roles;
	
	private String[] roleNames;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}


	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}
	
	
}
