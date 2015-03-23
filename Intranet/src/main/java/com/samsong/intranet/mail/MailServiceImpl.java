package com.samsong.intranet.mail;

import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService{
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private MailServiceDAO dao;
	
	private SimpleMailMessage simpleMessage;
	
	private MimeMessage mimeMessage;
	
	@Override
	public void sendMailText(String subject, String text, String from, String to) {
		simpleMessage = new SimpleMailMessage();
		simpleMessage.setSubject(subject);
		simpleMessage.setText(text);
		simpleMessage.setFrom(from);
		simpleMessage.setTo(to);
		mailSender.send(simpleMessage);
	}

	@Override
	public void sendMailText(String subject, String text, String from, String to, String[] cc) {
		
	}

	@Override
	public void sendMailHTML(String subject, String htmlText, String from, String recipient) {
		mimeMessage = mailSender.createMimeMessage();
		try {
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(recipient));
			mimeMessage.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			mimeMessage.setText(htmlText, "utf-8", "html");
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMailHTML(String subject, String htmlText, String from, String recipient, String[] cc) {
		
	}

	@Override
	public String getEmailAddress(String empNo) {
		Map<String, Object> map = dao.getEmailAddress(empNo);
		return map.get("email").toString();
	}
}
