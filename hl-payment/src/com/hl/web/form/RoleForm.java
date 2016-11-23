package com.hl.web.form;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hl.common.model.BaseBean;


public class RoleForm extends BaseBean {
	
	private String id;

	@NotEmpty
	@Length(min = 2, max = 32)
	private String name;

	@Length(min = 0, max = 128)
	private String description;
	
	private String[] funcs;
	
	private String[] funcNames;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getFuncs() {
		return funcs;
	}

	public void setFuncs(String[] funcs) {
		this.funcs = funcs;
	}

	public String[] getFuncNames() {
		return funcNames;
	}

	public void setFuncNames(String[] funcNames) {
		this.funcNames = funcNames;
	}

}
