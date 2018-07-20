package com.hh.herb.member.model;

public interface MemberDAO {
	public int checkDuplicate(String userid);
	public int insertMember(MemberVO vo);
	public String selectPwd(String userid);
	public MemberVO selectMember(String userid);
	public int updateMember(MemberVO vo);
	public int memberOut(String userid);
	
}
