package com.hh.herb.cart.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
	@Autowired private CartDAO cartDao;
	
	@Override
	public int insertCart(CartVO cartVo) {
		int cnt=0;
		
		int count=cartDao.selectCountCart(cartVo);
		//이미 해당 고객이 해당 상품을 장바구니에 담은 경우 - 수량 update
		if(count>0) {
			cnt=cartDao.updateCartQty(cartVo);
		}else {
			cnt=cartDao.insertCart(cartVo);
		}
		
		return cnt;
	}

	@Override
	public List<Map<String, Object>> selectCartView(String userid) {
		return cartDao.selectCartView(userid);
	}

	@Override
	public int editCart(CartVO cartVo) {
		return cartDao.editCart(cartVo);
	}

	
}



