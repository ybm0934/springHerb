package com.hh.herb.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.herb.category.model.CategoryService;
import com.hh.herb.category.model.CategoryVO;
import com.hh.herb.common.FileUploadUtil;
import com.hh.herb.common.PaginationInfo;
import com.hh.herb.common.Utility;
import com.hh.herb.product.model.EventProductVO;
import com.hh.herb.product.model.ProductListVO;
import com.hh.herb.product.model.ProductService;
import com.hh.herb.product.model.ProductVO;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
	private static final Logger logger
		=LoggerFactory.getLogger(AdminProductController.class);	
	
	@Autowired private CategoryService categoryService;
	@Autowired private FileUploadUtil fileUploadUtil;
	@Autowired private ProductService productService;
	
	@RequestMapping(value="/product/productWrite.do", method=RequestMethod.GET)
	public void productWrite(Model model) {
		logger.info("관리자- 상품 등록 화면");
		
		List<CategoryVO> list =categoryService.selectCategoryAll();
		logger.info("관리자- 카테고리 조회 결과 list.size={}", list.size());
		
		model.addAttribute("list", list);
	}

	@RequestMapping(value="/product/productWrite.do", method=RequestMethod.POST)
	public String productWrite_post(@ModelAttribute ProductVO vo,
			HttpServletRequest request, Model model) {
		logger.info("관리자- 상품 등록, 파라미터 vo={}", vo);
		
		//파일 업로드
		String fileName="";
		try {
			List<Map<String, Object>> list
			  =fileUploadUtil.fileUpload(request, FileUploadUtil.PATH_FLAG_IMAGE);
			for(Map<String, Object> map:list) {
				fileName =(String) map.get("fileName");				
			}
			vo.setImageURL(fileName);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//db
		int cnt =productService.insertProduct(vo);
		logger.info("상품등록 결과, cnt={}", cnt);
		
		String msg="", url="/admin/product/productWrite.do";
		if(cnt>0) {
			//msg="상품등록하였습니다.";
			url="/admin/product/productList.do";
		}else {
			msg="상품등록 실패";
		}//if
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/product/productList.do")
	public String productList(@ModelAttribute EventProductVO searchVo,
			Model model) {
		logger.info("상품 목록, 파라미터 searchVo={}", searchVo);
		
		//[1] PaginationInfo 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		
		//[2] SearchVo 추가 셋팅
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		logger.info("셋팅 후 searchVo={}", searchVo);
		
		List<ProductVO> list=productService.selectAll(searchVo);
		logger.info("상품 목록 조회 결과 list.size={}", list.size());
		
		int totalRecord =productService.selectTotalRecord(searchVo);
		logger.info("상품 목록 개수 :{}", totalRecord);
		pagingInfo.setTotalRecord(totalRecord);
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "admin/product/productList";
	}
	
	@RequestMapping("/product/deleteMulti.do")
	public String deleteMulti(@ModelAttribute ProductListVO pdListVo,
			HttpServletRequest request, Model model) {
		logger.info("선택한 상품 삭제, 파라미터 pdListVo={}", pdListVo);
		
		List<ProductVO> list=pdListVo.getPdItems();
		int cnt=productService.deleteMulti(list);
		logger.info("다중삭제 결과, cnt={}", cnt);
		
		String msg="", url="/admin/product/productList.do";
		if(cnt>0) {		
			for(int i=0;i<list.size();i++) {
				ProductVO vo =list.get(i);
				logger.info(i+" : productNo={}, imageURL={}",vo.getProductNo(),
						vo.getImageURL());
				
				//선택한 상품 이미지 삭제
				if(vo.getProductNo()>0) {
					File file 
= new File(fileUploadUtil.getUploadPath(request, FileUploadUtil.PATH_FLAG_IMAGE), vo.getImageURL());
					if(file.exists()) {
						boolean bool=file.delete();
						logger.info("이미지 삭제 여부:{}", bool);
					}
				}
			}//for
			
			msg="다중 삭제 처리되었습니다.";
		}else {
			msg="다중 삭제처리 중 에러 발생!";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/product/eventMulti.do")
	public String eventMulti(@ModelAttribute ProductListVO productListVo,
			@RequestParam String eventSel, Model model) {
		logger.info("이벤트로 등록, 파라미터 productListVo={}", productListVo);
		
		List<ProductVO> list =productListVo.getPdItems();
		
		int cnt=productService.eventMulti(list, eventSel);
		logger.info("이벤트로 등록 결과, cnt={}", cnt);
		
		String msg="", url="/admin/product/productList.do";
		if(cnt>0) {
			msg="해당 상품들을 이벤트 상품으로 등록하였습니다.";
		}else {
			msg="이벤트 상품으로 등록 실패!";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	
}










