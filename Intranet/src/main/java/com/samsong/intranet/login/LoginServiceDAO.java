package com.samsong.intranet.login;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.samsong.common.DBPrefix;

@Repository
public class LoginServiceDAO {
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = DBPrefix.WEBDESK;
	@Autowired
	public void setDataSource(DataSource ds){
		this.jdbc = new JdbcTemplate(ds);
	}
	
	public String checkUser(String username, String birth){
		String sql = "EXEC"+DB_PREFIX_WEBDESK+"MOBILE_LOGIN_checkUser ?,?";
		return jdbc.queryForObject(sql, new Object[]{username, birth},String.class);
	}

	public void insertUser(String username, String password) {
		String sql = "INSERT INTO "+DB_PREFIX_WEBDESK+"MOBILE_AUTH_USER VALUES(?,?,'ROLE_USER');";
		jdbc.update(sql, username, password);
	}

	public void insertLog(String username, String action) {
		String sql = "EXEC "+DB_PREFIX_WEBDESK+"MOBILE_LOG_INSERT ?,?";
		jdbc.update(sql, username, action);
	}

	public void updatePassword(String username, String password) {
		String sql = "UPDATE "+DB_PREFIX_WEBDESK+"MOBILE_AUTH_USER SET password=? WHERE username=?";
		jdbc.update(sql, password, username);
	}
}
