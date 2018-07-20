package com.hh.herb.board.controller;

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
public class BoardEditController {
	private static final Logger logger
	= LoggerFactory.getLogger(BoardDetailController.class);

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String edit_get(@RequestParam(defaultValue="0") int no, Model model) {
		logger.info("수정화면 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/board/list.do");
			return "common/message";
		}
		
		BoardVO vo=boardService.selectByNo(no);
		logger.info("수정화면 상세보기 결과, vo={}", vo);
		
		model.addAttribute("vo", vo);
		
		return "board/edit";
	}
	
	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute BoardVO boardVo, Model model) {
		logger.info("글수정 처리, 파라미터 vo={}", boardVo);
		
		String msg="", url="/board/edit.do?no="+boardVo.getNo();
		if(boardService.checkPwd(boardVo.getNo(), boardVo.getPwd())) {
			int cnt=boardService.updateBoard(boardVo);
			logger.info("글수정 결과, cnt={}", cnt);
			if(cnt>0) {
				msg="글 수정 성공";			
				url="/board/detail.do?no="+boardVo.getNo();
			}else {
				msg="글 수정 실패";
			}			
		}else {
			msg="비밀번호가 일치하지 않습니다.";			
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	
}




