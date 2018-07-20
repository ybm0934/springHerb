package com.hh.herb.member.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOMybatis implements MemberDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	private String namespace="config.mybatis.mapper.oracle.member.";
	
	@Override
	public int checkDuplicate(String userid) {
		return sqlSession.selectOne(namespace+"checkDuplicate", userid);
	}
	
	public int insertMember(MemberVO vo) {
		return sqlSession.insert(namespace+"insertMember", vo);
	}

	@Override
	public String selectPwd(String userid) {
		return sqlSession.selectOne(namespace+"selectPwd", userid);
	}

	@Override
	public MemberVO selectMember(String userid) {
		return sqlSession.selectOne(namespace+"selectMember", userid);
	}

	@Override
	public int updateMember(MemberVO vo) {
		return sqlSession.update(namespace+"updateMember", vo);
	}

	@Override
	public int memberOut(String userid) {
		return sqlSession.update(namespace+"memberOut", userid);
	}
	
	
}









