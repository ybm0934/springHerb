package com.hh.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hh.herb.board.model.BoardService;
import com.hh.herb.board.model.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardWriteController {
	private static final Logger logger
		=LoggerFactory.getLogger(BoardWriteController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write_get() {
		//handler
		logger.info("글쓰기 화면");
		
		return "board/write";  //논리적 뷰이름 리턴
	}

	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String write_post(@ModelAttribute BoardVO boardVo) {
		logger.info("글쓰기 처리, 파라미터 vo={}", boardVo);
		
		int cnt=boardService.insertBoard(boardVo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		return "redirect:/board/list.do";
	}
	
}






