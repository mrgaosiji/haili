package com.hl.web.form;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hl.common.model.BaseBean;


public class ModifyPwdForm extends BaseBean {

	@NotEmpty
	@Length(min=1,max=32)
	private String password;
	
	@NotEmpty
	@Length(min=1,max=32)
	private String password1;
	
	@NotEmpty
	@Length(min=1,max=32)
	private String password2;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
}
