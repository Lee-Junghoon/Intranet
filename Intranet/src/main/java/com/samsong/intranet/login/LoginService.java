package com.samsong.intranet.login;

public interface LoginService {
	public String checkUser(String username, String birth);	//사번이 있는지 체크하는 메소드
	public void insertUser(String username, String password);	//사원 등록 
	public void insertLog(String username, String action);	//사용자 로그인 기록
	public void updatePassword(String username, String password); // 임시 비밀번호 발급
	public String makePassword(String password);	//비밀번호 생성
	public String makeTempPassword();	//임시 비밀번호 생성
}
