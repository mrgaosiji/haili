package com.hl.web.security;


import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.hl.common.model.BaseBean;


public class AuthUser extends BaseBean implements Serializable {

	private static final long serialVersionUID = -2847795358893630030L;

	public static final String AUTH_USER_SESSION_KEY = "key.session.auth.user";

	private List<String> funcCodeList;
	
	private Object data;
	
	private String id;
	
	private Date lastLoginTime;
	
	private int loginCount;

	public boolean isPermitted(String url) {
		if (StringUtils.hasText(url) && funcCodeList != null
				&& !funcCodeList.isEmpty()) {
			url = url.toLowerCase();
			for (String func : funcCodeList) {
				String funcCode = func.toLowerCase();
				if (funcCode.length() >= 3 && url.endsWith(funcCode)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isContains(String code) {
		if (StringUtils.hasText(code) && funcCodeList != null
				&& !funcCodeList.isEmpty()) {
			for (String func : funcCodeList) {
				if (code.equalsIgnoreCase(func)) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static Object getCurrentUser(HttpServletRequest req){
		AuthUser au=(AuthUser) req.getSession().getAttribute(AUTH_USER_SESSION_KEY);
		if(au!=null){
			return  au.getData();
		}
		return null;
	}

	public List<String> getFuncCodeList() {
		return funcCodeList;
	}

	public void setFuncCodeList(List<String> funcCodeList) {
		if (funcCodeList != null)
			this.funcCodeList = Collections.synchronizedList(funcCodeList);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
}
