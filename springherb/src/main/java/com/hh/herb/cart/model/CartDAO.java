package com.hh.herb.cart.model;

import java.util.List;
import java.util.Map;

public interface CartDAO {
	public int selectCountCart(CartVO cartVo);
	public int updateCartQty(CartVO cartVo);
	public int insertCart(CartVO cartVo);
	public List<Map<String, Object>> selectCartView(String userid);
	public int editCart(CartVO cartVo);
	public int deleteCartByUserid(String userid);
	
}
