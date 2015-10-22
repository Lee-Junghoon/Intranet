package com.samsong.intranet.home;

import java.util.List;
import java.util.Map;

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
	public Map<String, Object> getNoticeTop1() {
		String sql = "select top 1 num[id],subject,writer,pdsfile[file],reg_date[regDate],content from "+DB_PREFIX_WEBDESK+" noticeboard order by num desc";
		return jdbc.queryForMap(sql);
	}
	public List<Map<String, Object>> getEducationTop5() {
		StringBuffer sb = new StringBuffer();
		sb.append("select top 5 boardNo,category,subcategory,title,content,writer,");
		sb.append("(select emp_no from "+DB_PREFIX_WEBDESK+" person_master_view where retire_dt is null and emp_nm=writer)[empNo] ");
		sb.append("from "+DB_PREFIX_WEBDESK+" education_board order by boardno desc");
		return jdbc.queryForList(sb.toString());
	}
	public String getEmpPhoto(String empNo) {
		String sql = "select '/settings/thumbnail?path='+emp_photo[empPhoto] from "+DB_PREFIX_WEBDESK+" person_master_view where emp_no=?";
		return jdbc.queryForObject(sql,new String[] {empNo}, String.class);
	}
}
