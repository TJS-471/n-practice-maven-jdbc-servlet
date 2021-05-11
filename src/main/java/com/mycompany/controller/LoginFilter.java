package com.mycompany.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A web servlet class that implements {@link Filter}.
 * It intercepts GET and POST requests from a client and performs request blocking based on user identity.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebFilter("/admin/*")
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false);
		boolean loggedin = session != null && session.getAttribute("login") != null;
		String loginURI = httpServletRequest.getContextPath() + "/login";
		boolean logingRequest = httpServletRequest.getRequestURI().equals(loginURI);

		if (loggedin || logingRequest) {

			chain.doFilter(request, response);
		} else {
			String destUrl = httpServletRequest.getContextPath() + "/login";
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.sendRedirect(destUrl);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
