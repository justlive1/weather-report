package com.github.justlive1.demo.weather.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * Utils - Web
 * 
 * @author wubo
 *
 */
public final class WebUtils {

	public static String getIpAddr(final HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 应对x-forwarded-for 中返回多个服务器ip的情况
		if (StringUtils.hasText(ip) && (ip.split(",").length > 1)) {
			String[] ips = ip.split(",");
			for (String a : ips) {
				if (StringUtils.hasText(a) && !("unknown").equalsIgnoreCase(a.trim())) {
					ip = a.trim();
					break;
				}
			}
		}
		return ip;
	}
}
