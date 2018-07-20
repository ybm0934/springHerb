package com.hh.herb.cart.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOMybatis implements CartDAO{
	@Autowired private SqlSessionTemplate sqlSession;
	
	private String namespace="config.mybatis.mapper.oracle.cart.";
	
	@Override
	public int selectCountCart(CartVO cartVo) {
		return sqlSession.selectOne(namespace+"selectCountCart", cartVo);
	}

	@Override
	public int updateCartQty(CartVO cartVo) {
		return sqlSession.update(namespace+"updateCartQty", cartVo);
	}

	@Override
	public int insertCart(CartVO cartVo) {
		return sqlSession.insert(namespace+"insertCart",cartVo);
	}

	@Override
	public List<Map<String, Object>> selectCartView(String userid) {
		return sqlSession.selectList(namespace+"selectCartView", userid);
	}

	@Override
	public int editCart(CartVO cartVo) {
		return sqlSession.update(namespace+"editCart", cartVo);
	}

	@Override
	public int deleteCartByUserid(String userid) {
		return sqlSession.delete(namespace+"deleteCartByUserid", userid);
	}

	
}










