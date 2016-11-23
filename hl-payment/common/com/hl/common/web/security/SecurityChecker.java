package com.hl.common.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * 
 */
public interface SecurityChecker {

	int PERMIT_RESULT_CODE_SUCCESS = 10000;

	int PERMIT_RESULT_CODE_NOT_LOGIN = 10001;

	int PERMIT_RESULT_CODE_NO_PERMIT = 10002;
	

	int isSessionPermit(HttpServletRequest request, HttpServletResponse response,String funcCode)
			throws IOException, ServletException;
	
	/**
	 * 判断当前请求是否合法，允许访问管理系统
	 * @param request
	 * @param response
	 * @param funcCode
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	int isPermit(HttpServletRequest request, HttpServletResponse response,String funcCode)
			throws IOException, ServletException;

	/**
	 * 访问被拒绝时操作处理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	void onDeny(HttpServletRequest request, HttpServletResponse response,
			int resultCode) throws IOException, ServletException;
	
	
	void onSessionDeny(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;



}