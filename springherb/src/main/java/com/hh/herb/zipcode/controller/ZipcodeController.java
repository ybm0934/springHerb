package com.hh.herb.zipcode.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.herb.common.PaginationInfo;
import com.hh.herb.common.Utility;
import com.hh.herb.zipcode.model.ZipcodeService;
import com.hh.herb.zipcode.model.ZipcodeVO;

@Controller
public class ZipcodeController {
	private static final Logger logger
	=LoggerFactory.getLogger(ZipcodeController.class);
	
	@Autowired
	private ZipcodeService zipcodeService;
	
	/*@RequestMapping("/zipcode/zipcode.do")
	public String zipcode(@ModelAttribute ZipcodeVO searchVo, 
				Model model) {
		logger.info("우편번호 찾기, 파라미터 searchVo={}", searchVo);
		
		List<ZipcodeVO> list=null;
		
		//[1] PaginationInfo 객체 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_TEN);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] 파라미터인 SearchVO 계열 객체에 값 setting
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_TEN);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("settring 후 searchVo={}", searchVo);
		
		if(searchVo.getDong()!=null && !searchVo.getDong().isEmpty()) {
			list=zipcodeService.selectZipcode(searchVo);
			logger.info("우편번호 찾기 결과, list.size={}", list.size());
			
			int totalRecord
				=zipcodeService.selectTotalRecord(searchVo.getDong());
			logger.info("우편번호 찾기-totalRecord={}", totalRecord);
			
			pagingInfo.setTotalRecord(totalRecord);
		}
				
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "zipcode/zipcode";
	}*/
	
	@RequestMapping("/zipcode/zipcode.do")
	public String zipcode() {
		logger.info("우편번호 찾기");
				
		return "zipcode/zipcode";
	}
	
	
}




