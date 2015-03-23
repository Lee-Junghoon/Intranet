package com.samsong.intranet.home;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.samsong.common.DBPrefix;

@Repository
public class HomeServiceDAO {
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = DBPrefix.WEBDESK;
	@Autowired
	public void setDataSource(DataSource ds){
		this.jdbc = new JdbcTemplate(ds);
	}
}
