package com.hl.web.form;

import org.hibernate.validator.constraints.Length;

public class UserSearchForm extends BaseListQueryForm {

	@Length(min=0,max=32)
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
