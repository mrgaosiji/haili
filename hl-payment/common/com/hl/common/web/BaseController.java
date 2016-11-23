package com.hl.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseController {

	protected Log log = LogFactory.getLog(getClass());

	protected String errorMsgLineSeparator = ";";

	protected String resolveFieldLabel(FieldError fe, HttpServletRequest request) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());

		String label = null;

		for (Object arg : fe.getArguments()) {
			if (arg instanceof DefaultMessageSourceResolvable) {
				DefaultMessageSourceResolvable dmsr = (DefaultMessageSourceResolvable) arg;
				label = ctx.getMessage(dmsr, null);
				if (label != null) {
					break;
				}
			}
		}

		return label;
	}

	protected String resolveFieldErrorMsg(FieldError fe,
			HttpServletRequest request) {
		// resolveFieldLabel(fe, request) +
		return fe.getDefaultMessage();
	}

	protected String transFieldErrorToMsg(BindingResult result,
			HttpServletRequest request) {
		return this
				.transFieldErrorToMsg(result, request, errorMsgLineSeparator);
	}

	protected String transFieldErrorToMsg(BindingResult result,
			HttpServletRequest request, String msgLineSeparator) {
		if (result != null) {
			List<FieldError> es = result.getFieldErrors();
			if (es != null && !es.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (FieldError e : es) {
					sb.append(resolveFieldLabel(e, request));
					sb.append(" ");
					sb.append(resolveFieldErrorMsg(e, request));
					sb.append(msgLineSeparator);
				}
				return sb.toString();
			}
		}
		return null;
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null) {
			String[] ipAry = ip.split(",");
			if (ipAry.length > 1) {
				for (int i = 0; i < ipAry.length; i++) {
					if (!"unknown".equalsIgnoreCase(ip)) {
						ip = ipAry[i];
						break;
					}
				}
			}
		}
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
