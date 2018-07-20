package com.hh.herb.product.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOMybatis implements ProductDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="config.mybatis.mapper.oracle.product.";
	
	@Override
	public List<ProductVO> selectEventProduct(String eventName) {
		return sqlSession.selectList(namespace+"selectEventProduct", eventName);
	}

	@Override
	public ProductVO selectProduct(int productNo) {
		return sqlSession.selectOne(namespace+"selectProduct", productNo);
	}

	@Override
	public int insertProduct(ProductVO vo) {
		return sqlSession.insert(namespace+"insertProduct", vo);
	}

	@Override
	public List<ProductVO> selectAll(EventProductVO searchVo) {
		return sqlSession.selectList(namespace+"selectAll", searchVo);
	}

	@Override
	public int selectTotalRecord(EventProductVO searchVo) {
		return sqlSession.selectOne(namespace+"selectTotalRecord", searchVo);
	}

	@Override
	public int deleteProduct(int productNo) {
		return sqlSession.delete(namespace+"deleteProduct", productNo);
	}

	@Override
	public int selectEventCount(EventProductVO vo) {
		return sqlSession.selectOne(namespace+"selectEventCount", vo);
	}

	@Override
	public int insertEventProduct(EventProductVO vo) {
		return sqlSession.insert(namespace+"insertEventProduct", vo);
	}

	
	
}




