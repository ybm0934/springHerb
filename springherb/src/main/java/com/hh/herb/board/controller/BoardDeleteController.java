package com.hh.herb.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.herb.board.model.BoardService;
import com.hh.herb.board.model.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardDeleteController {
	private static final Logger logger
		=LoggerFactory.getLogger(BoardDeleteController.class);
	
	@Autowired private BoardService boardService;
	
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public String delete(@RequestParam(defaultValue="0") int no,
			Model model) {
		logger.info("글삭제 화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg","잘못된 URL 입니다.");
			model.addAttribute("url","/board/list.do");
			return "common/message";
		}
		
		return "board/delete";
	}
	
	@RequestMapping(value="/delete.do", method=RequestMethod.POST)
	public String delete_post(@ModelAttribute BoardVO boardVo, Model model) {
		logger.info("글삭제 처리, 파라미터 vo={}", boardVo);
		
		String msg="", url="/board/delete.do?no="+boardVo.getNo();
		if(boardService.checkPwd(boardVo.getNo(), boardVo.getPwd())) {
			int cnt=boardService.deleteBoard(boardVo);
			if(cnt>0) {
				msg="글 삭제 성공";
				url="/board/list.do";
			}else {
				msg="글 삭제 실패";
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "common/message";
	}
	
	@RequestMapping("/deleteMulti.do")
	public String deleteMulti(@RequestParam String[] chk, Model model) {
		logger.info("여러 글 삭제");
		
		//String[] chkArr = request.getParameterValues("chk");
		if(chk!=null) {
			int i=0;
			for(String no : chk) {
				logger.info("{} : 파라미터 => {}", i++, no);
			}
		}//if
		
		Map<String, String[]> map = new HashMap<>();
		map.put("nos", chk);
		int cnt=boardService.deleteMulti(map);
		logger.info("여러 글 삭제 결과, cnt={}", cnt);
		
		String msg="", url="/board/list.do";
		if(cnt>0) {
			msg="여러 글 삭제 성공";
		}else {
			msg="여러 글 삭제 실패";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	
}










