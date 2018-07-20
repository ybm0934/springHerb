package com.hh.herb.order.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.hh.herb.cart.model.CartService;
import com.hh.herb.common.DateSearchVO;
import com.hh.herb.common.PaginationInfo;
import com.hh.herb.common.Utility;
import com.hh.herb.member.model.MemberService;
import com.hh.herb.member.model.MemberVO;
import com.hh.herb.order.model.OrderAllVO;
import com.hh.herb.order.model.OrderService;
import com.hh.herb.order.model.OrderVO;

@Controller
@RequestMapping("/shop/order")
public class OrderController {
	private static final Logger logger
		=LoggerFactory.getLogger(OrderController.class);
	
	@Autowired private CartService cartService;
	@Autowired private MemberService memberService;
	@Autowired private OrderService orderService;
	
	@RequestMapping(value="/orderSheet.do", method=RequestMethod.GET)
	public String orderSheet(HttpSession session, Model model) {
		String userid =(String) session.getAttribute("userid");
		logger.info("주문하기 화면, 파라미터 userid={}", userid);
		
		//주문서 작성 - 장바구니 목록
		List<Map<String, Object>> cartList=cartService.selectCartView(userid);
		logger.info("주문하기 - 장바구니 목록 결과, list.size={}", cartList.size());
		
		//주문하시는 분 - 로그인한 회원정보
		MemberVO memberVo =memberService.selectMember(userid);
		logger.info("주문하기 - 회원조회, 결과 vo={}", memberVo);
		
		model.addAttribute("cartList", cartList);
		model.addAttribute("memberVo", memberVo);
		model.addAttribute("TOTAL_PRICE", CartService.TOTAL_PRICE);
		model.addAttribute("DELIVERY", CartService.DELIVERY);
		
		return "shop/order/orderSheet";
	}
	
	@RequestMapping(value="/orderSheet.do", method=RequestMethod.POST)
	public String orderSheet_post(@ModelAttribute OrderVO orderVo, HttpSession session) {
		String userid =(String) session.getAttribute("userid");
		orderVo.setCustomerId(userid);		
		logger.info("주문하기, 파라미터 orderVo={}", orderVo);
		
		int cnt=orderService.insertOrder(orderVo);
		logger.info("주문하기 결과, cnt={}", cnt);
		
		//주문완료 페이지로 이동
		return "redirect:/shop/order/orderComplete.do?orderNo="+orderVo.getOrderNo();
	}
	
	@RequestMapping("/orderComplete.do")
	public String orderComplete(@RequestParam(defaultValue="0") int orderNo, Model model) {
		logger.info("주문완료 페이지, 파라미터 orderNo={}", orderNo);
		
		if(orderNo==0) {
			model.addAttribute("msg","잘못된 url입니다.");
			model.addAttribute("url","/index.do");
			return "common/message";					
		}
		
		List<Map<String, Object>> list=orderService.selectOrderDetailsView(orderNo);
		logger.info("주문한 상품 결과 list.size={}", list.size());
		
		Map<String, Object> map=orderService.selectOrdersView(orderNo);
		logger.info("배송정보 결과, map={}", map);
		
		model.addAttribute("list", list);
		model.addAttribute("ordersMap", map);
		
		return "shop/order/orderComplete";		
	}
	
	@RequestMapping("/orderList.do")
	public String orderList(@ModelAttribute DateSearchVO dateSearchVo, HttpSession session,
			Model model) {
		String userid =(String)session.getAttribute("userid");
		dateSearchVo.setCustomerId(userid);		
		logger.info("주문내역, 파라미터 dateSearchVo={}", dateSearchVo);
		
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(dateSearchVo.getCurrentPage());		
		
		dateSearchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		dateSearchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		
		//날짜가 입력되지 않은 경우 현재일자 셋팅
		String startDay=dateSearchVo.getStartDay();
		if(startDay==null || startDay.isEmpty()) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateSearchVo.setStartDay(sdf.format(d));
			dateSearchVo.setEndDay(sdf.format(d));			
			logger.info("setting 후 searchVo={}", dateSearchVo );
		}
		
		List<OrderAllVO> list=orderService.selectOrderList(dateSearchVo);
		logger.info("주문내역 결과!!, list.size={}", list.size());
		
		pagingInfo.setTotalRecord(orderService.selectTotalRecord(dateSearchVo));
		
		//주문번호에 해당하는 주문 상세 내역 처리
/*		
		for(OrderAllVO orderAllVo : list) {
			int orderNo=orderAllVo.getOrderVo().getOrderNo();
			List<Map<String, Object>> detailsList=orderService.selectOrderDetailsView(orderNo);
			orderAllVo.setOrderDetailsList(detailsList);
		}//for
*/		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "shop/order/orderList";
	}

	
}

