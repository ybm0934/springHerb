package com.hh.herb.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/ajaxTest")
public class AjaxTestController {
	private static final Logger logger
		=LoggerFactory.getLogger(AjaxTestController.class);
	
	@RequestMapping("/ajaxTest1.do")
	public void ajaxTest1() {
		logger.info("ajaxTest1.jsp 화면 보여주기");		
	}
	
	@RequestMapping("/search.do")
	@ResponseBody
	public String search(@RequestParam(required=false) String id, 
			@RequestParam(required=false) String keyword) {
		logger.info("ajax-search.do 요청 처리, 파라미터 id={}, keyword={}",id,
				keyword);
		
		String result=id+","+keyword+",sbs,sk,sm";
		return result;
	}
	
	@RequestMapping("ajaxTest2.do")
	public void ajaxTest2() {
		logger.info("ajaxTest2.jsp 화면 보여주기");
	}
	
	@RequestMapping("/list1.do")
	@ResponseBody
	public List<MemoVO> list1(){
		logger.info("list1.do 처리");
		
		List<MemoVO> list = new ArrayList<>();
		list.add(new MemoVO(1, "홍길동", "안녕"));
		list.add(new MemoVO(2, "김길동", "안녕하세요"));
		list.add(new MemoVO(3, "이길동", "안녕!!!"));
		
		return list;  //list를 json포맷으로 변환해서 http응답의
		//메시지 본문으로 전환됨
		
		//[{"no":1,"name":"홍길동","content":"안녕"},{"no":2,"name":"김길동","content":"안녕하세요"},{"no":3,"name":"이길동","content":"안녕!!!"}]
	}
	
	@RequestMapping("detail1.do")
	@ResponseBody
	public MemoVO detail1(@RequestParam int no) {
		logger.info("ajax-detail1.do 처리, 파라미터 no={}", no);
		
		MemoVO vo = new MemoVO(no, "홍길동","안녕");
		
		return vo;
		
		//{"no":5,"name":"홍길동","content":"안녕"}
	}
	
	@RequestMapping("/ajaxTest3.do")
	public void ajaxTest3() {
		logger.info("ajaxTest3 화면 보여주기");		
	}
	
	@RequestMapping("/ajaxTest4.do")
	public void ajaxTest4() {
		logger.info("ajaxTest4 화면 보여주기");		
	}
	
	@RequestMapping("/memoWrite.do")
	@ResponseBody
	public ResultVO memoWrite(@ModelAttribute MemoVO memoVo) {
		logger.info("memoWrite.do 처리, 파라미터 vo={}", memoVo);
		
		memoVo.setNo(10);
		
		ResultVO vo = new ResultVO();
		vo.setMessage("등록 성공");
		vo.setResult(memoVo);
		
		return vo;
		//{"message":"등록 성공","result":{"no":10,"name":"김길동","content":"안녕"}}
	}
	
	
	

	
}





