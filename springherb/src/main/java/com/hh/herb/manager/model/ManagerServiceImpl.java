package com.hh.herb.manager.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.herb.member.model.MemberService;

@Service
public class ManagerServiceImpl implements ManagerService{
	@Autowired private ManagerDAO managerDao;
	
	@Override
	public List<Map<String, String>> selectAuthority() {
		return managerDao.selectAuthority();
	}

	@Override
	public int insertManager(ManagerVO vo) {
		return managerDao.insertManager(vo);
	}

	@Override
	public boolean duplicateUserid(String userid) {
		int count =managerDao.duplicateUserid(userid);
		if(count>0) {
			return false;
		}else {
			return true;  //사용가능
		}
	}

	@Override
	public int loginCheck(String userid, String pwd) {
		String dbPwd=managerDao.selectPwd(userid);
		if(dbPwd!=null && !dbPwd.isEmpty()) {
			if(dbPwd.equals(pwd)) {
				return MemberService.LOGIN_OK;
			}else {
				return MemberService.PWD_DISAGREE;
			}
		}else {
			return MemberService.ID_NONE;			
		}//if		
	}

	@Override
	public ManagerVO selectManager(String userid) {
		return managerDao.selectManager(userid);
	}

	
}





