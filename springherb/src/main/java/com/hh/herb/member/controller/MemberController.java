package com.hh.herb.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hh.herb.member.model.MemberService;
import com.hh.herb.member.model.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static final Logger logger
		=LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/agreement.do")
	public String agreement() {
		logger.info("회원약관 화면 보여주기");
		
		return "member/agreement";
	}
	
	@RequestMapping("/register.do")
	public void register() {
		logger.info("회원가입 화면 보여주기");
	}
	
	@RequestMapping("/checkUserid.do")
	public String checkUserid(@RequestParam String userid, Model model) {
		logger.info("아이디 중복확인, 파라미터 userid={}", userid);
		
		int result=0;
		if(userid!=null && !userid.isEmpty()) {
			result=memberService.checkDuplicate(userid);
		}
		logger.info("아이디 중복확인 결과, result={}", result);
		
		model.addAttribute("result", result);
		model.addAttribute("EXIST_ID", MemberService.EXIST_ID);
		model.addAttribute("AVAILABLE_ID", MemberService.AVAILABLE_ID);
		
		return "member/checkUserid";
	}
	
	@RequestMapping("/ajaxCheckId.do")
	@ResponseBody
	public boolean ajaxCheckId(@RequestParam String userid) {
		logger.info("ajax-아이디 중복확인, 파라미터 userid={}", userid);
		
		int result=memberService.checkDuplicate(userid);
		logger.info("아이디 중복확인 결과, result={}", result);
		
		boolean bool=false;  //사용불가
		if(result==MemberService.AVAILABLE_ID) {
			bool=true;  //사용가능
		}
		
		return bool;
	}
	
	@RequestMapping("/memberWrite.do")
	public String memberWrite(@ModelAttribute MemberVO memberVo, 
			@RequestParam String email3, Model model) {
		logger.info("회원가입 처리 파라미터 vo={}, email3={}", memberVo, email3);
		
		String hp2=memberVo.getHp2();
		String hp3=memberVo.getHp3();		
		if(hp2==null || hp2.isEmpty() || hp3==null || hp3.isEmpty()) {
			memberVo.setHp1("");
			memberVo.setHp2("");
			memberVo.setHp3("");
		}
		
		String email1=memberVo.getEmail1();
		if(email1==null || email1.isEmpty()) {
			memberVo.setEmail2("");
		}else {
			if(memberVo.getEmail2().equals("etc")) {
				if(email3!=null && !email3.isEmpty()) {
					memberVo.setEmail2(email3);
				}else {
					memberVo.setEmail1("");
					memberVo.setEmail2("");					
				}
			}
		}//if
		
		int cnt=memberService.insertMember(memberVo);
		logger.info("회원가입 결과, cnt={}", cnt);
		
		String msg="", url="";
		if(cnt>0) {
			msg="회원가입되었습니다.";
			url="/index.do";
		}else {
			msg="회원가입 실패!";
			url="/member/register.do";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/memberEdit.do", method=RequestMethod.GET)
	public String edit_get(HttpSession session, ModelMap model) {
		String userid=(String) session.getAttribute("userid");
		logger.info("회원정보 수정 화면, 파라미터 userid={}", userid);
		
		/*if(userid==null || userid.isEmpty()) {
			model.addAttribute("msg","먼저 로그인하세요");
			model.addAttribute("url","/login/login.do");
			
			return "common/message";
		}*/
		
		MemberVO vo =memberService.selectMember(userid);
		logger.info("회원정보 조회 결과, vo={}", vo);
		
		model.addAttribute("vo", vo);
		
		return "member/memberEdit";		
	}
	
	@RequestMapping(value="/memberEdit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute MemberVO vo, 
			@RequestParam(required=false) String email3, HttpSession session, 
			ModelMap model) {
		String userid =(String) session.getAttribute("userid");
		vo.setUserid(userid);
		
		logger.info("회원 수정처리, 파라미터 vo={}", vo);
		
		int result=memberService.loginCheck(vo.getUserid(), vo.getPwd());
		String msg="", url="/member/memberEdit.do";
		if(result==MemberService.LOGIN_OK) {
			//수정처리
			if(vo.getHp2()==null || vo.getHp2().isEmpty() 
					|| vo.getHp3()==null || vo.getHp3().isEmpty()) {
				vo.setHp1("");
				vo.setHp2("");
				vo.setHp3("");				
			}
			
			if(vo.getEmail1()==null || vo.getEmail1().isEmpty()) {
				vo.setEmail2("");
			}else {
				if(vo.getEmail2().equals("etc")) {
					if(email3!=null && !email3.isEmpty()) {
						vo.setEmail2(email3);
					}else {
						vo.setEmail1("");
						vo.setEmail2("");
					}
				}
			}//if
			
			int cnt=memberService.updateMember(vo);
			msg = (cnt>0)?"회원정보가 수정되었습니다.":"회원정보 수정 실패";
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else {
			msg="비밀번호 체크 실패.";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/memberOut.do", method=RequestMethod.GET)
	public void memberOut() {
		logger.info("회원탈퇴 화면 보여주기");
	}

	@RequestMapping(value="/memberOut.do", method=RequestMethod.POST)
	public String memberOut_post(@RequestParam String pwd,
			HttpSession session, HttpServletResponse response, 
			Model model) {
		String userid=(String) session.getAttribute("userid");
		logger.info("회원탈퇴처리, 파라미터 userid={}", userid);
		
		String msg="", url="/member/memberOut.do";
		int result =memberService.loginCheck(userid, pwd);
		logger.info("비밀번호 체크 결과, result={}", result);
		if(result==MemberService.LOGIN_OK) {
			int cnt=memberService.memberOut(userid);
			logger.info("회원탈퇴처리 결과, cnt={}", cnt);
			if(cnt>0) {
				//session 제거
				session.invalidate();
				
				//cookie 제거
				Cookie ck = new Cookie("ck_userid", userid);
				ck.setPath("/");
				ck.setMaxAge(0);
				response.addCookie(ck);
				
				msg="회원탈퇴처리되었습니다.";
				url="/index.do";
			}else {
				msg="회원탈퇴 실패";
			}
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else {
			msg="비밀번호 체크 실패.";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
		
	}
	
	
}











