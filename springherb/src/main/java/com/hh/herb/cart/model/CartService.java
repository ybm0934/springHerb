package com.hh.herb.cart.model;

import java.util.List;
import java.util.Map;

public interface CartService {
	//총구매금액이 3만원 미만이면 배송비 3000원
	public static final int TOTAL_PRICE=30000;  //총 구매금액
	int DELIVERY=3000;  //배송비
	
	public int insertCart(CartVO cartVo);
	public List<Map<String, Object>> selectCartView(String userid);
	public int editCart(CartVO cartVo);
	
}
