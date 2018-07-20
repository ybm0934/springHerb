package com.hh.herb.manager.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDAOMybatis implements ManagerDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private String namespace="config.mybatis.mapper.oracle.manager.";
	
	@Override
	public List<Map<String, String>> selectAuthority() {
		return sqlSession.selectList(namespace+"selectAuthority");
	}

	@Override
	public int insertManager(ManagerVO vo) {
		return sqlSession.insert(namespace+"insertManager", vo);
	}

	@Override
	public int duplicateUserid(String userid) {
		return sqlSession.selectOne(namespace+"duplicateUserid", userid);
	}

	@Override
	public String selectPwd(String userid) {
		return sqlSession.selectOne(namespace+"selectPwd", userid);
	}

	@Override
	public ManagerVO selectManager(String userid) {
		return sqlSession.selectOne(namespace+"selectManager", userid);
	}

	
	
}




