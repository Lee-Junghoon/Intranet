package com.samsong.intranet.mail;

public interface MailService {
	public void sendMailText(String subject, String text, String from, String to);
	public void sendMailText(String subject, String text, String from, String to, String[] cc);
	public void sendMailHTML(String subject, String htmlText, String from, String recipient);
	public void sendMailHTML(String subject, String htmlText, String from, String recipient, String[] cc);
	public String getEmailAddress(String empNo);
}
