package com.hh.herb.product.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.herb.order.model.OrderService;
import com.hh.herb.product.model.ProductService;
import com.hh.herb.product.model.ProductVO;

@Controller
@RequestMapping("/shop/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/productEvent.do")
	public String productEvent(@RequestParam String eventName, Model model) {
		logger.info("이벤트 상품 보여주기, 파라미터 eventName={}", eventName);

		List<ProductVO> list = productService.selectEventProduct(eventName);
		logger.info("이벤트 상품 조회 결과 list.size={}", list.size());

		model.addAttribute("list", list);

		return "shop/product/productEvent";
	}

	@RequestMapping("/productDetail.do")
	public String productDetail(@RequestParam(defaultValue = "0") int productNo, Model model) {
		logger.info("상품 상세보기, 파라미터 productNo={}", productNo);

		if (productNo == 0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/index.do");
			return "common/message";
		}

		ProductVO productVo = productService.selectProduct(productNo);
		logger.info("상품 상세보기 결과, vo={}", productVo);

		model.addAttribute("vo", productVo);

		return "shop/product/productDetail";
	}

	@RequestMapping("/productImage.do")
	public void productImage(@ModelAttribute ProductVO vo) {
		logger.info("큰 이미지 보여주기, 파라미터 vo={}", vo);
	}

	@RequestMapping("/best5.do")
	public String best5(@RequestParam(defaultValue = "0") int productNo, Model model) {
		logger.info("카테고리별 판매가 가장 많은 상품 조회, 파라미터 productNo={}", productNo);

		if (productNo == 0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/index.do");
			return "common/message";
		}

		List<Map<String, Object>> list = orderService.selectBestProduct(productNo);
		logger.info("판매 best5 결과, list.size={}", list.size());

		model.addAttribute("list", list);

		return "inc/best5";
	}

}
