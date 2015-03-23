package com.samsong.intranet.settings;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsServiceDAO {
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = "[210.216.217.248].[WEBDESK].[dbo].";
	@Autowired
	public void setDataSource(DataSource ds){
		this.jdbc = new JdbcTemplate(ds);
	}
	
	public void changePassword(String username, String newPassword) {
		String sql = "EXEC "+ DB_PREFIX_WEBDESK+"[MOBILE_SETTINGS_changePassword] ?, ?";
		jdbc.update(sql, username, newPassword);
	}

	public String getPassword(String username) {
		String sql = "SELECT password FROM "+DB_PREFIX_WEBDESK+"MOBILE_AUTH_USER WHERE username=?";
		return jdbc.queryForObject(sql, new Object[]{username}, String.class);
	}
}
