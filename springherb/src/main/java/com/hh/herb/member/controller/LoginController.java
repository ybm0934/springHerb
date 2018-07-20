package com.hh.herb.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.herb.member.model.MemberService;
import com.hh.herb.member.model.MemberVO;

@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger
		=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login_get() {
		logger.info("로그인 화면 보여주기");
		
		return "login/login";		
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login_post(@ModelAttribute MemberVO vo, 
			@RequestParam(required=false) String saveId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		logger.info("로그인 처리, 파라미터 vo={}, saveId={}", vo, saveId);
		
		int result =memberService.loginCheck(vo.getUserid(), vo.getPwd());
		logger.info("로그인 처리 결과, result={}", result);
		
		String msg="", url="/login/login.do";
		if(result==MemberService.LOGIN_OK) {
			//[1] 세션에 저장
			//회원정보 읽어오기
			MemberVO memberVo =memberService.selectMember(vo.getUserid());
			request.getSession().setAttribute("userid", vo.getUserid());
			request.getSession().setAttribute("userName", memberVo.getName());
			
			//[2] 아이디 저장하기에 체크되었으면 쿠키에 저장
			Cookie ck = new Cookie("ck_userid", vo.getUserid());
			ck.setPath("/");
			if(saveId!=null) {
				ck.setMaxAge(1000*24*60*60);  //쿠키 유효기간 1000일
				response.addCookie(ck);
			}else {
				ck.setMaxAge(0);  //쿠키 삭제
				response.addCookie(ck);
			}
			
			msg=memberVo.getName()+ "님 로그인되었습니다.";
			url="/index.do";
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else if(result==MemberService.ID_NONE) {
			msg="해당 아이디가 존재하지 않습니다.";
		}else {
			msg="로그인 처리 실패";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		//session.invalidate();
		session.removeAttribute("userid");
		session.removeAttribute("userName");
		
		return "redirect:/index.do";
	}
	
	 
}







