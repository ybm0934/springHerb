package com.hh.herb.manager.model;

import java.util.List;
import java.util.Map;

public interface ManagerService {
	public List<Map<String, String>> selectAuthority();
	public int insertManager(ManagerVO vo);
	public boolean duplicateUserid(String userid);
	public int loginCheck(String userid, String pwd);
	public ManagerVO selectManager(String userid);
	
}
