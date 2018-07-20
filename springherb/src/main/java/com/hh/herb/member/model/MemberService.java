package com.hh.herb.member.model;

public interface MemberService {
	//아이디 중복확인에서 사용하는 상수
	public static final int EXIST_ID=1; //해당 아이디가 이미 존재
	int AVAILABLE_ID=2;  //사용가능
	
	//로그인 처리에서 사용
	int LOGIN_OK=1;  //로그인 성공
	int ID_NONE=2;  //해당 아이디가 없다
	int PWD_DISAGREE=3;  //비밀번호가 일치하지 않는다
		
	public int checkDuplicate(String userid);
	public int insertMember(MemberVO vo);
	public int loginCheck(String userid, String pwd);
	public MemberVO selectMember(String userid);
	public int updateMember(MemberVO vo);
	public int memberOut(String userid);
	
}
