package com.samsong.intranet.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService{
	
	private StandardPasswordEncoder encoder;
	
	public SettingsServiceImpl() {
		this.encoder = new StandardPasswordEncoder();
	}
	
	@Autowired
	private SettingsServiceDAO dao;

	@Override
	public boolean changePassword(String username, String currentPassword, String newPassword) {
		if(checkPassword(username, currentPassword)){
			dao.changePassword(username, encoder.encode(newPassword));
			return true;
		}
		
		return false;
	}

	@Override
	public boolean checkPassword(String username, String currentPassword) {
		return encoder.matches(currentPassword, dao.getPassword(username));
	}
}
