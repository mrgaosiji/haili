package com.hl.common.web.security;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityHandlerInterceptor implements  HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(handler!=null&&handler instanceof HandlerMethod){
			HandlerMethod hm=(HandlerMethod) handler;
			AuthCfg authCfg=hm.getMethodAnnotation(AuthCfg.class);
			if(authCfg!=null&&!authCfg.auth()){
				return true;
			}
			ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			SecurityChecker checker=null;
			try {
				checker=ctx.getBean(SecurityFilter.SECURITY_CHECKER_BEAN, SecurityChecker.class);
			} catch (Exception e) {
			}
			if(checker!=null){
				int rtn=checker.isPermit(request, response, authCfg!=null?authCfg.code():null);
				if(rtn!=SecurityChecker.PERMIT_RESULT_CODE_SUCCESS){
					request.setAttribute("authTag", "SecurityHandlerInterceptor");
					checker.onDeny(request, response, rtn);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
