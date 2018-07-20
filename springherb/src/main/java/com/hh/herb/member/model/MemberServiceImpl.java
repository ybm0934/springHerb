package com.hh.herb.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO memberDao;

	@Override
	public int checkDuplicate(String userid) {
		int result = memberDao.checkDuplicate(userid);
		if(result>0) {
			return EXIST_ID;
		}else {
			return AVAILABLE_ID;
		}
	}

	public int insertMember(MemberVO vo){
		int cnt = memberDao.insertMember(vo);
		return cnt;
	}

	@Override
	public int loginCheck(String userid, String pwd) {
		int result=0;
		String dbPwd = memberDao.selectPwd(userid);
		if(dbPwd==null || dbPwd.isEmpty()) {
			result=ID_NONE;  //해당 아이디가 없다
		}else {
			if(dbPwd.equals(pwd)) {
				result=LOGIN_OK;
			}else {
				result=PWD_DISAGREE;
			}
		}
		return result;
	}

	@Override
	public MemberVO selectMember(String userid) {
		return memberDao.selectMember(userid);
	}

	@Override
	public int updateMember(MemberVO vo) {
		return memberDao.updateMember(vo);
	}

	@Override
	public int memberOut(String userid) {
		return memberDao.memberOut(userid);
	}
	
	
}








