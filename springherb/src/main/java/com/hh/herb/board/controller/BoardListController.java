package com.hh.herb.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.herb.board.model.BoardService;
import com.hh.herb.board.model.BoardVO;
import com.hh.herb.common.PaginationInfo;
import com.hh.herb.common.SearchVO;
import com.hh.herb.common.Utility;

@Controller
@RequestMapping("/board")
public class BoardListController {
	private static final Logger logger
		=LoggerFactory.getLogger(BoardListController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/list.do")
	public String list(@ModelAttribute SearchVO searchVo,  Model model) {
		logger.info("글 목록, 파라미터 searchVo={}", searchVo);
		
		//[1] PaginationInfo 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] SearchVO 에 값 셋팅
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("setting 후 searchVo={}", searchVo);
		
		List<BoardVO> list =boardService.selectAll(searchVo);
		logger.info("글 목록 조회 결과, list.size={}", list.size());
		
		//전체 레코드 개수 조회
		int totalRecord=boardService.getTotalRecord(searchVo);
		pagingInfo.setTotalRecord(totalRecord);
		logger.info("전체 레코드 개수={}", totalRecord);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVo", pagingInfo);
		
		return "board/list";
	}
	
}





