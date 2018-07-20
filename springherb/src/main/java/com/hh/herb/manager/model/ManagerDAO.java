package com.hh.herb.manager.model;

import java.util.List;
import java.util.Map;

public interface ManagerDAO {
	public List<Map<String, String>> selectAuthority();
	public int insertManager(ManagerVO vo);
	public int duplicateUserid(String userid);
	public String selectPwd(String userid);
	public ManagerVO selectManager(String userid);
	
	
}
