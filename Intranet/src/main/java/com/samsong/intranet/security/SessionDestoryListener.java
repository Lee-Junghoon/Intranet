package com.samsong.intranet.security;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;

import com.samsong.intranet.user.User;

//2014.12.04 IP-LJH
//세션 타임아웃 발생하면 sessiondestroy 이벤트 리스너가 동작
public class SessionDestoryListener implements ApplicationListener<SessionDestroyedEvent>{
	public static final Logger logger = LoggerFactory.getLogger(SessionDestoryListener.class);
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = "[210.216.217.248].[WEBDESK].[dbo].";
	public SessionDestoryListener(DataSource datasource){
		this.jdbc = new  JdbcTemplate(datasource);
	}
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		List<SecurityContext> contexts = event.getSecurityContexts();
		if (contexts.isEmpty() == false) {
			for (SecurityContext ctx : contexts) {
				User user = (User)ctx.getAuthentication().getPrincipal();
				String sql = "EXEC "+DB_PREFIX_WEBDESK+"MOBILE_LOG_INSERT ?,?";
				jdbc.update(sql, user.getEmpNo(), "LOGOUT");
				logger.info(user.getEmpName()+"님이 로그아웃 하였습니다.");
			}
		}

	}

}
