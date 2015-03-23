package com.samsong.intranet.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	private String loginIdName;
	private String loginIdPassword;
	private String loginRedirectName;
	private String exceptionMsgName;
	private String defaultFailureUrl;
	private static Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);
	public LoginFailureHandler() {
		this.loginIdName = "j_username";
		this.loginIdPassword = "j_password";
		this.loginRedirectName = "loginRedirect";
		this.exceptionMsgName = "securityExceptionMsg";
		this.defaultFailureUrl = "/login";
	}

	public String getLoginIdName() {
		return loginIdName;
	}

	public void setLoginIdName(String loginIdName) {
		this.loginIdName = loginIdName;
	}

	public String getLoginIdPassword() {
		return loginIdPassword;
	}

	public void setLoginIdPassword(String loginIdPassword) {
		this.loginIdPassword = loginIdPassword;
	}

	public String getLoginRedirectName() {
		return loginRedirectName;
	}

	public void setLoginRedirectName(String loginRedirectName) {
		this.loginRedirectName = loginRedirectName;
	}

	public String getExceptionMsgName() {
		return exceptionMsgName;
	}

	public void setExceptionMsgName(String exceptionMsgName) {
		this.exceptionMsgName = exceptionMsgName;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String loginId = request.getParameter(loginIdName);
		String loginPassword = request.getParameter(loginIdPassword);
		String loginRedirect = request.getParameter(loginRedirectName);
		
		logger.info(loginId + " 사용자 로그인 실패 (ERROR:"+exception.getMessage()+")");
		
		request.setAttribute(loginIdName, loginId);
		request.setAttribute(loginIdPassword, loginPassword);
		request.setAttribute(loginRedirectName, loginRedirect);
		request.setAttribute(exceptionMsgName, "아이디 또는 비밀번호를 다시 확인하세요.인트라넷에 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.");
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
}
