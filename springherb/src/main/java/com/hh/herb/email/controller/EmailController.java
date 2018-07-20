package com.hh.herb.email.controller;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.herb.email.EmailSender;

@Controller
@RequestMapping("/emailTest")
public class EmailController {
	private static final Logger logger=LoggerFactory.getLogger(EmailController.class);
	
	@Autowired private EmailSender emailSender;
		
	@RequestMapping("/emailTest1.do")
	public void emailTest1() {
		logger.info("이메일 발송 화면 보여주기");
	}
	
	@RequestMapping("/send.do")
	public String send() {
		logger.info("이메일 발송 처리");
		
		String subject="문의에 대한 답변입니다. 안녕하세요";
		String content="이메일 내용입니다. 감사합니다.";
		String to="now4ever7@gmail.com";
		String from="admin@herbmall.com";
		
		try {
			emailSender.sendEmail(subject, content, to, from);
			logger.info("이메일 발송 성공");
		} catch (MessagingException e) {
			logger.info("이메일 발송 실패");
			e.printStackTrace();
		}
		
		return "redirect:/index.do";
	}
	
	
}




