package com.hl.web.security;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis2.databinding.types.xsd.DateTime;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import com.hl.common.model.RespInfo;
import com.hl.common.web.JsonTextView;
import com.hl.common.web.security.SecurityChecker;


public class UrlSecurityChecker implements SecurityChecker {

	private List<String> includeUrls;

	private List<String> excludeUrls;

	private PathMatcher pathMatcher = new AntPathMatcher();

	
	@Override
	public int isSessionPermit(HttpServletRequest request,
			HttpServletResponse response, String funcCode)throws IOException,
			ServletException {
		
		System.out.println("<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>isSessionPermit111111");
		
		response.addHeader("Cache-Control", "no-store, must-revalidate"); 
		
		response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
		
		HttpSession session = request.getSession(false);

		System.out.println("<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>判断SESSION是不是为NULL");

		
		if (session!=null) {

			System.out.println("session id是:" + session.getId());

			String userInfo = (String) session.getAttribute("userInfo");

			if (userInfo == null) {
				System.out.println("userInfo信息为空:" + session.getId());
				return 0;
			}
		} else {
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!session为NULL");
			
//			System.out.println("session过期了,重新创建session:" + new DateTime());
//			request.getSession(true); //create session
			return 0;
		}
				
		System.out.println("<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>判断SESSION没有任何结果!!!!!");
		
		return 1;
	}
	
	@Override
	public int isPermit(HttpServletRequest request,
			HttpServletResponse response, String funcCode) throws IOException,
			ServletException {
		response.addHeader("Cache-Control", "no-store, must-revalidate"); 
		response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
//		if("q".equalsIgnoreCase("q")){
//			System.out.println("===req uri:"+request.getRequestURI());
//			return PERMIT_RESULT_CODE_SUCCESS;//for test,disable the checker
//		}
		AuthUser user = (AuthUser) request.getSession().getAttribute(AuthUser.AUTH_USER_SESSION_KEY);
		
		String url = request.getRequestURI();
		
		String ctxPath = request.getContextPath();

		// 非网址方式的功能编码验证
		if (StringUtils.hasText(funcCode)) {
			if (user == null) {
				return PERMIT_RESULT_CODE_NOT_LOGIN;
			} else if (!user.isContains(funcCode)) {
				//return PERMIT_RESULT_CODE_NO_PERMIT;
				//继续地址方式判断
			} else {
				return PERMIT_RESULT_CODE_SUCCESS;
			}
		}

		// 网址方式功能编码验证
		if (ctxPath != null && ctxPath.length() > 0) {
			url = url.substring(ctxPath.length());
		}
		
		if (excludeUrls != null) {
			for (String p : excludeUrls) {
				if (pathMatcher.match(p, url)) {
					return PERMIT_RESULT_CODE_SUCCESS;
				}
			}
		}
		
		if (includeUrls != null) {
			for (String p : includeUrls) {
				if (pathMatcher.match(p, url)) {
					// check
					if (user == null) {
						return PERMIT_RESULT_CODE_NOT_LOGIN;
					} else {
						if (!user.isPermitted(url)) {
							return PERMIT_RESULT_CODE_NO_PERMIT;
						}
					}
				}
			}
		}
		return PERMIT_RESULT_CODE_SUCCESS;
	}
	
	

	@Override
	public void onSessionDeny(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			ServletException {
		
		System.out.println("dataType:"+request.getParameter("dataType"));
		
		if ("json".equalsIgnoreCase(request.getParameter("dataType"))
				|| "json".equalsIgnoreCase(request.getHeader("dataType"))) {
			
			System.out.println("回话过期了~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			try {
							
				request.setAttribute(JsonTextView.KEY_BEAN_DATA,
						new RespInfo(601, "用户未登录或会话已过期，请重新登录", null));
				
				new JsonTextView().render(null, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("始终返回有问题，返回原地址~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			//response.sendRedirect(request.getContextPath() + "/login.html");
			response.getWriter().append("<script type=\"text/javascript\" >");
			response.getWriter().append("(top.window||window).location='"+"/index.jsp';");//+request.getContextPath()
			response.getWriter().append("</script>");
			request.getSession(true); //create session
		}
	}
	
	@Override
	public void onDeny(HttpServletRequest request,
			HttpServletResponse response, int resultCode) throws IOException,
			ServletException {
		switch (resultCode) {
		case PERMIT_RESULT_CODE_NOT_LOGIN: {
			
			System.out.println("dataType:"+request.getParameter("dataType"));
			
			if ("json".equalsIgnoreCase(request.getParameter("dataType"))
					|| "json".equalsIgnoreCase(request.getHeader("dataType"))) {
				try {
					request.setAttribute(JsonTextView.KEY_BEAN_DATA,
							new RespInfo(601, "用户未登录或会话已过期，请重新登录", null));
					new JsonTextView().render(null, request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//response.sendRedirect(request.getContextPath() + "/login.html");
				response.getWriter().append("<script type=\"text/javascript\" >");
				response.getWriter().append("(top.window||window).location='"+request.getContextPath()+"/manage-login.html';");
				response.getWriter().append("</script>");
				
			}
			
			break;
		}
		case PERMIT_RESULT_CODE_NO_PERMIT: {
			if ("json".equalsIgnoreCase(request.getParameter("dataType"))
					|| "json".equalsIgnoreCase(request.getHeader("dataType"))) {
				try {
					request.setAttribute(JsonTextView.KEY_BEAN_DATA,
							new RespInfo(601, "当前用户没有访问操作权限", null));
					new JsonTextView().render(null, request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		default: {
			// throw new ServletException("当前用户没有访问操作权限");
			request.setAttribute("javax.servlet.error.status_code",
					"601/当前用户没有访问操作权限");
			request.setAttribute("javax.servlet.error.request_uri",
					request.getRequestURI());
			request.getSession().getServletContext()
					.getRequestDispatcher("/error.jsp")
					.forward(request, response);
		}
		}
	}

	public List<String> getIncludeUrls() {
		return includeUrls;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setIncludeUrls(List<String> includeUrls) {
		this.includeUrls = includeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
