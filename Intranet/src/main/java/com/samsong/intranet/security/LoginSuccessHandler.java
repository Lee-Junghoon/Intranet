package com.samsong.intranet.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.samsong.intranet.user.User;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	public static final int TARGET_URL = 0;
	public static final int SESSION_URL = 1;
	public static final int REFERER_URL = 2;
	public static final int DEFAULT_URL = 3;
	
	public String targetUrlParameter;
	public String defaultUrl;
	public boolean useReferer;
	
	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}
	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}
	public boolean isUseReferer() {
		return useReferer;
	}
	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	public LoginSuccessHandler(){
		targetUrlParameter = "";
		defaultUrl = "/";
		useReferer = false;
	}
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response){
		int result = DEFAULT_URL;
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(!"".equals(targetUrlParameter)){
			String targetUrl = request.getParameter(targetUrlParameter);
			if(StringUtils.hasText(targetUrl)){
				result = SESSION_URL;
			}else{
				if(savedRequest != null){
					result = REFERER_URL;
				}else{
					String refererUrl = request.getHeader("REFERER");
					if(useReferer && StringUtils.hasText(refererUrl)){
						result = DEFAULT_URL;
					}else{
						result = TARGET_URL;
					}
				}
			}
			return result;
		}
		
		if(savedRequest != null){
			result = REFERER_URL;
			return result;
		}
		
		String refererUrl = request.getHeader("REFERER");
		if(useReferer && StringUtils.hasText(refererUrl)){
			result = DEFAULT_URL;
		}else{
			result = TARGET_URL;
		}
		return result;
	}	
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null){
			requestCache.removeRequest(request, response);
		}
		String targetUrl = request.getParameter(targetUrlParameter);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = savedRequest.getRedirectUrl();
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String targetUrl = request.getHeader("REFERER");
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException{
		redirectStrategy.sendRedirect(request, response, defaultUrl);
	}
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
    	User user = (User) auth.getPrincipal();
    	clearAuthenticationAttributes(request);
    	int intRedirectStrategy = decideRedirectStrategy(request, response);
    	switch(intRedirectStrategy){
	    	case 1: 
	    		useTargetUrl(request, response); 
	    		break;
	    	case 2: 
	    		useSessionUrl(request, response); 
	    		break;
	    	case 3: 
	    		useRefererUrl(request, response); 
	    		break;
	    	default : 
	    		useDefaultUrl(request, response); 
	    		break;
    	}

    	logger.info("["+user.getDeptName()+"]"+user.getEmpName() + "님이 로그인 하였습니다.");
    }
}
