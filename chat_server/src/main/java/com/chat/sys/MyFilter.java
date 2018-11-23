package com.chat.sys;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.fastjson.JSON;


/**
 * ������
 * @author Administrator
 *
 */
//@WebFilter(filterName = "myfilter", urlPatterns = "/*")
public class MyFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(getClass());


	@Value("${com.chat.develop}")
	private Boolean develop;

	@Value("${com.chat.userid}")
	private String userid;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (develop) {
			testDoFilter(req, resp, chain);
		} else {
			sysDoFilter(req, resp, chain);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	public void testDoFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//String uri = request.getRequestURI();
		//Student student = studentMapper.findOne(userid);
		//request.setAttribute("student", student);
		chain.doFilter(request, response);

	}

	public void sysDoFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 获取请求中是否包含session
		String miniSession = request.getParameter("miniSession");
		// 功能权限过滤
		String uri = request.getRequestURI();
		logger.debug("ip地址:" + request.getHeader("X-Real-IP"));
		if (uri.startsWith("/wdxy/a/")) {
			chain.doFilter(request, response);
		} else {
			if (miniSession != null && !miniSession.equals("")) {
				// 1、新接口
				//String uid = redisService.getStr(miniSession);
				String uid = "";
				if (uid != null) {
					//Student student = studentMapper.findOne(userid);
					//redisService.setStrAsMINUTES(miniSession, uid, 30);
					// 将登录用户对象加入request�?
					//request.setAttribute("student", student);
					chain.doFilter(request, response);
				} else {
					noLogin(response, "未登�?");
				}
			} else {
				noLogin(response, "未登�?");
			}
		}
	}

	public void noLogin(HttpServletResponse response, String text) {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		//RMessage message = new RMessage();
//		message.setCode(401);
//		message.setMessage(text);
		//String j = JSON.toJSONString(message);
		try {
			PrintWriter writer = response.getWriter();
			//writer.print(j);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
