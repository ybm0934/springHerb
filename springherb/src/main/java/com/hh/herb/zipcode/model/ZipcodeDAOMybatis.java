package com.hh.herb.zipcode.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZipcodeDAOMybatis implements ZipcodeDAO{

	@Autowired
	private SqlSessionTemplate sqlSession;

	private String namespace="config.mybatis.mapper.oracle.zipcode.";
	
	@Override
	public List<ZipcodeVO> selectZipcode(ZipcodeVO vo) {
		return sqlSession.selectList(namespace+"selectZipcode", vo);
	}

	@Override
	public int selectTotalRecord(String dong) {
		return sqlSession.selectOne(namespace+"selectTotalRecord", dong);
	}
	
	
}



