package com.hh.herb.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hh.herb.manager.model.ManagerService;
import com.hh.herb.manager.model.ManagerVO;
import com.hh.herb.member.model.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger
		=LoggerFactory.getLogger(AdminController.class);
	
	@Autowired private ManagerService managerService;
		
	@RequestMapping("/adminMain.do")
	public void adminMain() {
		logger.info("관리자 메인 페이지 보여주기");
	}

	@RequestMapping(value="/manager/register.do", method=RequestMethod.GET)
	public void register(Model model) {
		logger.info("관리자 등록 화면 보여주기");
		
		List<Map<String, String>> list=managerService.selectAuthority();
		logger.info("권한 조회, 결과 list.size={}", list.size());
		
		model.addAttribute("list", list);		
	}
	
	@RequestMapping(value="/manager/register.do", method=RequestMethod.POST)
	public String register_post(@ModelAttribute ManagerVO vo , Model model) {
		logger.info("관리자 등록 처리, 파라미터 vo={}", vo);
		
		int cnt=managerService.insertManager(vo);
		logger.info("관리자 등록 결과, cnt={}", cnt);
		
		String msg="", url="/admin/manager/register.do";
		if(cnt>0) {
			msg="관리자 등록되었습니다.";
		}else {
			msg="관리자 등록 실패";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/manager/ajaxAdminCheckId.do")
	@ResponseBody
	public boolean checkId(@RequestParam String userid) {
		logger.info("ajax - 관리자 아이디 중복확인, 파라미터 userid={}", userid);
		
		boolean bool =managerService.duplicateUserid(userid);
		logger.info("아이디 중복확인 결과, bool={}", bool);
		
		return bool;
	}
	
	@RequestMapping(value="/login/login.do", method=RequestMethod.GET)
	public void login() {
		logger.info("관리자 로그인 화면 보여주기");		
	}
	
	@RequestMapping(value="/login/login.do", method=RequestMethod.POST)
	public String login_post(@ModelAttribute ManagerVO vo, 
			@RequestParam(required=false) String saveId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("로그인 처리, 파라미터 vo={}", vo);
		
		int result=managerService.loginCheck(vo.getUserid(), vo.getPwd());
		logger.info("로그인 처리 결과, result={}", result);
		
		String msg="", url="/admin/login/login.do";
		if(result==MemberService.LOGIN_OK) {
			ManagerVO managerVo=managerService.selectManager(vo.getUserid());
			logger.info("로그인 처리- 관리자 조회 결과, vo={}", managerVo);
			
			//session에 저장
			HttpSession session=request.getSession();
			session.setAttribute("adminUserid", vo.getUserid());
			session.setAttribute("adminUserName", managerVo.getName());
			session.setAttribute("adminAuthCode", managerVo.getAuthCode());
			
			//쿠키에 저장
			Cookie ck = new Cookie("ck_admin_userid", vo.getUserid());
			ck.setPath("/");
			if(saveId!=null) {
				ck.setMaxAge(1000*24*60*60);
				response.addCookie(ck);
			}else {
				ck.setMaxAge(0);
				response.addCookie(ck);
			}
			
			msg=managerVo.getName() + "님 관리자 로그인되었습니다.";
			url="/admin/adminMain.do";
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else if(result==MemberService.ID_NONE ) {
			msg="해당 아이디가 없습니다.";
		}else {
			msg="로그인 처리 실패";			
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/login/logout.do")
	public String logout(HttpSession session) {
		logger.info("관리자 로그아웃 처리");
		
		session.removeAttribute("adminUserid");
		session.removeAttribute("adminUserName");
		session.removeAttribute("adminAuthCode");
		
		return "redirect:/admin/login/login.do";
	}
	
}










