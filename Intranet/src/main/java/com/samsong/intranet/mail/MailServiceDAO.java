package com.samsong.intranet.mail;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.samsong.common.DBPrefix;

@Repository
public class MailServiceDAO {
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = DBPrefix.WEBDESK;

	@Autowired
	public void init(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	public Map<String, Object> getEmailAddress(String empNo) {
		String sql = "select email from "+DB_PREFIX_WEBDESK+"emp_mail where uid=?";
		return jdbc.queryForMap(sql, empNo);
	}
}
