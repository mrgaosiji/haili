package com.hl.web.form;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hl.common.model.BaseBean;


public class LoginForm extends BaseBean {

	@NotEmpty
	@Length(min=2,max=32)
	private String userName;
	@NotEmpty
	@Length(min=1,max=32)
	private String password;
	//@NotEmpty
	private String verifyCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
