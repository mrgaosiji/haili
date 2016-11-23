package com.hl.web.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hl.common.model.BaseBean;



/**
 * The persistent class for the MM_USER database table.
 * 
 */
@Entity
@Table(name="MM_USER")
public class MmUser extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false, length=32)
	private String userId;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="ADD_TIME", nullable=false)
	private Date addTime;

	private String address;

	@Column(length=21)
	private String cellphone;

	@Column(length=64)
	private String email;

	@Column(name="FULL_NAME")
	private String fullName;

	@Column(precision=2)
	private Long gender;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGIN_TIME")
	private Date lastLoginTime;

	@Column(name="LOGIN_COUNT", precision=6)
	private Long loginCount;

	private String note;

	@Column(nullable=false, precision=2)
	private Long status;

	@Column(name="USER_PWD", nullable=false, length=64)
	private String userPwd;

	//uni-directional many-to-many association to MmRole
    @ManyToMany
	@JoinTable(
		name="MM_USER_ROLE_REL"
		, joinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="ROLE_ID", nullable=false)
			}
		)
	private List<MmRole> roles;


    public MmUser() {
    }

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getGender() {
		return this.gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public List<MmRole> getRoles() {
		return this.roles;
	}

	public void setRoles(List<MmRole> roles) {
		this.roles = roles;
	}
	
}