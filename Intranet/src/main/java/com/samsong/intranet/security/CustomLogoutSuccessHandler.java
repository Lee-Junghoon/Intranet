package com.samsong.intranet.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.samsong.intranet.user.User;

//2014.12.04 IP-LJH
//커스텀 로그아웃 구현했다가 세션 만료 구현하면서 커스텀 로그아웃 처리가 필요없어짐.
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
	public static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
	public String defaultUrl;
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = "[210.216.217.248].[WEBDESK].[dbo].";
	public CustomLogoutSuccessHandler(DataSource datasource){
		this.jdbc = new  JdbcTemplate(datasource);
	}
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException,	ServletException {
		User user = (User)auth.getPrincipal();
		
		if(auth != null && auth.getDetails() != null){
			try{
				//String sql = "EXEC "+DB_PREFIX_WEBDESK+"MOBILE_LOG_INSERT ?,?";
				//jdbc.update(sql, user.getEmpNo(), "LOGOUT");
				//logger.info(user.getEmpName() + "님이 로그아웃 하였습니다.");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		response.setStatus(response.SC_OK);
		response.sendRedirect(defaultUrl);
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}
