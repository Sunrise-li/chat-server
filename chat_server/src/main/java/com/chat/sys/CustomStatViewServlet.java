package com.chat.sys;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.support.http.StatViewServlet;


/**
 * 
 * @author Administrator
 *
 */
public class CustomStatViewServlet extends StatViewServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isPermittedRequest(HttpServletRequest request) {
		String remoteAddress = getIpAddr(request);
		return isPermittedRequest(remoteAddress);
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}

		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}