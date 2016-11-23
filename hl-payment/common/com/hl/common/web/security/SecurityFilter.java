package com.hl.common.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 权限验证
 * <p>spring security 框架比较复杂</p>
 *
 *
 */
public class SecurityFilter implements Filter {
	
	public static final String SECURITY_CHECKER_BEAN="securityFilterChecker";
	
	private Log log=LogFactory.getLog(getClass());
	
	private String excludedPages;       
	
	private String[] excludedPageArray;  
	
	SecurityChecker checker;

	@Override
	public void destroy() {
		

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println(">>>>>>>>>> start filter");
		
		
		
		if(checker!=null){
			
			boolean isPortal=false;
			
			HttpServletRequest hsreq=(HttpServletRequest)request;
			
			HttpServletResponse hsresp=(HttpServletResponse)response;
			
			for (String page : excludedPageArray) {//判断是否在过滤url之外     
				if(((HttpServletRequest) request).getServletPath().equals(page)){     
				  
					isPortal = true;     
				    
					break;     
				} 
			}
						
			if(isPortal==true)	
			{
				int isSessionPermit = checker.isSessionPermit(hsreq, hsresp, null);

				if(isSessionPermit==0){
					checker.onSessionDeny(hsreq, hsresp);
					return;
				}
				else
				{
					chain.doFilter(request, response);
					return;
				}
			}

//			int resultCode = checker.isPermit(hsreq, hsresp,null);
			
			
//			if(resultCode!=SecurityChecker.PERMIT_RESULT_CODE_SUCCESS){
//				checker.onDeny(hsreq, hsresp,resultCode);
//				return;
//			}
			
		}
		else
		{
			System.out.println(">>>>>>>>>> checker is null");
		}
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		
		excludedPages = cfg.getInitParameter("excludedPages");     
		
		if (StringUtils.isNotEmpty(excludedPages)) {     
		
			excludedPageArray = excludedPages.split(",");     
		}     
	    
		ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(cfg.getServletContext());
		try {
			checker=ctx.getBean(SECURITY_CHECKER_BEAN, SecurityChecker.class);
		} catch (Exception e) {
			log.error(""+e);
		}
		if(checker==null){
			log.warn("securityFilterChecker is null! ");
		}
	}

	
}
