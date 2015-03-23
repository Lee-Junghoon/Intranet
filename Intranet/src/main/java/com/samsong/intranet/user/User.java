package com.samsong.intranet.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class User implements UserDetails{
	
	private Collection<? extends GrantedAuthority> authorities;
	private String username;
	private String password;
	private String deptName;
	private String deptNo;
	private String empName;
	private String empNo;
	private String email;
	private String empPhoto;
	private String empCellNo;
	private String empInPhoneNo;//내선
	private String rankCode;	//직위코드
	private String rankName;	//직위
	private String positionCode;//직책코드
	private String positionName;//직책
	private String birthday;	//생일
	private String gender;	//성별
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	public User(Collection<? extends GrantedAuthority> authorities, String username, String password, String deptName,
			String deptNo, String empName, String empNo, String email, String empPhoto, String empCellNo, String empInPhoneNo,
			String rankCode, String rankName,String positionCode, String positionName, String birthday, String gender, 
			boolean accountNonExpired, boolean accountNonLocked,
			boolean credentialsNonExpired, boolean enabled)
	{
		this.authorities = authorities;
		this.username = username;
		this.password = password;
		this.deptName = deptName;
		this.deptNo = deptNo;
		this.empName = empName;
		this.empNo = empNo;
		this.email = email;
		this.empPhoto = empPhoto;
		this.empCellNo = empCellNo;
		this.empInPhoneNo = empInPhoneNo;
		this.rankCode = rankCode;
		this.rankName = rankName;
		this.positionCode = positionCode;
		this.positionName = positionName;
		this.birthday = birthday;
		this.gender = gender;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		//authority.add(new SimpleGrantedAuthority("ROLE_USER"));
		return this.authorities;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public String toString(){
		return "인트라넷 사용자 - authorities : "+this.authorities+
				", username : "+this.username+
				", password : "+this.password+
				", deptName : "+this.deptName+
				", deptNo : "+this.deptNo+
				", empName : "+this.empName+
				", empNo : "+this.empNo+
				", email : "+this.email+
				", empPhoto : "+this.empPhoto+
				", empCellNo : "+this.empCellNo+
				", accountNonExpired : "+this.accountNonExpired+
				", accountNonLocked : "+this.accountNonLocked+
				", credentialsNonExpired : "+this.credentialsNonExpired+
				", enabled : "+this.enabled+
				"]";
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public String getEmpName() {
		return empName;
	}

	public String getEmpNo() {
		return empNo;
	}

	public String getEmail() {
		return email;
	}

	public String getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getEmpCellNo() {
		return empCellNo;
	}

	public void setEmpCellNo(String empCellNo) {
		this.empCellNo = empCellNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birth) {
		this.birthday = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmpInPhoneNo() {
		return empInPhoneNo;
	}

	public void setEmpInPhoneNo(String empInPhoneNo) {
		this.empInPhoneNo = empInPhoneNo;
	}
	
}
