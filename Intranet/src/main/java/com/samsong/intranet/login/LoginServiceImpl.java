package com.samsong.intranet.login;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
	
	private StandardPasswordEncoder encoder;
	
	public LoginServiceImpl(){
		this.encoder = new StandardPasswordEncoder();
	}
	
	@Autowired
	private LoginServiceDAO dao;
	
	@Override
	public String checkUser(String username, String birth) {
		return dao.checkUser(username, birth);
	}

	@Override
	public void insertUser(String username, String password) {
		dao.insertUser(username, password);
	}

	@Override
	public void insertLog(String username, String action) {
		dao.insertLog(username, action);
	}

	@Override
	public void updatePassword(String username, String password) {
		dao.updatePassword(username, password);
	}

	@Override
	public String makePassword(String password) {
		return encoder.encode(password);
	}

	@Override
	public String makeTempPassword() {
		String [] letter = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		Collections.shuffle(Arrays.asList(letter));
		String temp = "";
		for(int i = 0; i < 8; i++){
			temp += letter[i];
		}
		return temp;
	}
}
