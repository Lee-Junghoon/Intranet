package com.samsong.intranet.user;

import java.util.*;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserServiceImpl implements UserDetailsService{
	
	private JdbcTemplate jdbc;
	
	public UserServiceImpl(DataSource datasource){
		super();
		this.jdbc = new  JdbcTemplate(datasource);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)	throws UsernameNotFoundException {
		//StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		//User user = new User();
		//user.setUsername(username);
		//user.setPassword(encoder.encode("1234"));	//DB 에서 가져와서 비교
		
		//System.out.println("username = " + user.getUsername());
		//System.out.println("password = " + user.getPassword());

		StringBuilder query = new StringBuilder();
		
		query.append("EXEC [210.216.217.248].[WEBDESK].[dbo].[MOBILE_LOGIN_getUserDetail] ?");
		//query.append("from(select username,[password],authority from mobile_auth_user where username=?)a "); 
		//query.append("join (select emp_no,emp_nm,emp_photo,dept_cd,rank from person_master_view) b on a.username=b.emp_no "); 
		//query.append("join (select dept_cd,dept_nm from code_dept_view)c on b.dept_cd=c.dept_cd ");
		//query.append("join (select uid,email from emp_mail)d on b.emp_no=d.uid ");
		//query.append("join (select rank_cd,rank_nm from code_rank_view)e on b.rank=e.rank_cd ");
		//query.append("join (select emp_no,cellular_no from [erpdata].[dbo].[person_address])f on b.emp_no=f.emp_no ");
		
		try{
			
			Map<String,Object> userData = jdbc.queryForMap(query.toString(), username);
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			authorities.add(new SimpleGrantedAuthority(userData.get("authority").toString()));
			
			return new User(authorities, 
					userData.get("username").toString(),
					userData.get("password").toString(),
					userData.get("deptName").toString(),
					userData.get("deptNo").toString(),
					userData.get("empName").toString(),
					userData.get("empNo").toString(),
					userData.get("email").toString(),
					userData.get("empPhoto").toString(),
					userData.get("cellularNo").toString(),
					userData.get("inPhoneNo").toString(),
					userData.get("rankCode").toString(),
					userData.get("rankName").toString(),
					userData.get("positionCode").toString(),
					userData.get("positionName").toString(),
					userData.get("birthday").toString(),
					userData.get("gender").toString(),
					true,true,true,true);
			
		}catch(EmptyResultDataAccessException e){
			return null;
			/*return new User(authorities, 
					"df3ea19424b09589d06ed35f727d5689f1ae5e19bdb582fb75af432b27bf9431e5fe7b699d6c0f2c",
					"df3ea19424b09589d06ed35f727d5689f1ae5e19bdb582fb75af432b27bf9431e5fe7b699d6c0f2c",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					true,true,true,true);*/
		}
	}

}
