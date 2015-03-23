package com.samsong.intranet.navigation;

import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.samsong.common.DBPrefix;

@Repository
public class NavigationServiceDAO {
	private JdbcTemplate jdbc;
	private static String DB_PREFIX_WEBDESK = DBPrefix.WEBDESK;
	@Autowired
	public void setDataSource(DataSource ds){
		this.jdbc = new JdbcTemplate(ds);
	}
	public List<Map<String, Object>> getEmployees() {
		String sql = "EXEC "+DB_PREFIX_WEBDESK+"MOBILE_EMPLOYEE_list";
		return jdbc.queryForList(sql);
	}
	public Map<String, Object> getEmpInfo(String empNo) {
		String sql = "EXEC "+DB_PREFIX_WEBDESK+"MOBILE_EMPLOYEE_info ?";
		return jdbc.queryForMap(sql, empNo);
	}
}
