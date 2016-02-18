package com.calow.cim.nio.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public FilterConfig config;

	@Override
	public void destroy() {
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();

		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();

		// 从session里取员工工号信息
		String user = (String) session.getAttribute("user");

		// 登陆页面无需过滤
		if (path.indexOf("/user/user_login.action") > -1) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}else{
			// 判断如果没有取到用户信息,就跳转到登陆页面
			if (user == null || "".equals(user)) {
				// 跳转到登陆页面
//			servletResponse.sendRedirect("/session/manage.jsp");
				chain.doFilter(request, response);
			} else {
				// 已经登陆,继续此次请求
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
