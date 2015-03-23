package com.samsong.intranet.settings;

public interface SettingsService {
	public boolean changePassword(String username, String currentPassword, String newPassword);
	public boolean checkPassword(String username, String currentPassword);
}
