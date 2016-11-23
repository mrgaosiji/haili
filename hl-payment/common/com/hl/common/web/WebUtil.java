package com.hl.common.web;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null) {
			String[] ips = ip.split(",");
			if (ips.length > 1) {
				for (int i = 0; i < ips.length; i++) {
					if (!"unknown".equalsIgnoreCase(ip)) {
						ip = ips[i];
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
