package com.hh.herb.category.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.herb.category.model.CategoryService;
import com.hh.herb.category.model.CategoryVO;

@Controller
public class CategoryController {
	private static final Logger logger
		=LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired private CategoryService categoryService;
	
	@RequestMapping("/inc/categoryList.do")
	public String categoryList(Model model) {
		logger.info("카테고리 목록 조회");
		
		List<CategoryVO> list=categoryService.selectCategoryAll();
		logger.info("카테고리 조회 결과, list.size={}", list.size());
		
		model.addAttribute("list", list);
		
		return "inc/categoryList";
	}
	
	
}




