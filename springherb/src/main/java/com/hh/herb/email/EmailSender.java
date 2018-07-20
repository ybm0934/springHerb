package com.hh.herb.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	@Autowired private JavaMailSender mailSender;
	
	public void sendEmail(String subject, String content, String to, String from) 
			throws MessagingException {
		//이메일 발송
		MimeMessage mimeMsg =mailSender.createMimeMessage();
		mimeMsg.setSubject(subject);
		mimeMsg.setText(content);
		mimeMsg.setRecipient(RecipientType.TO, new InternetAddress(to));
		mimeMsg.setFrom(new InternetAddress(from));
		
		mailSender.send(mimeMsg);		
	}
	
}







