package com.hh.herb.reboard.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.herb.common.SearchVO;

@Service
public class ReBoardServiceImpl implements ReBoardService{
	@Autowired
	private ReBoardDAO reBoardDao;
	
	public int insertReBoard(ReBoardVO vo) {
		return reBoardDao.insertReBoard(vo);
	}
		
	public List<ReBoardVO> selectAll(SearchVO searchVo){
		return reBoardDao.selectAll(searchVo);
	}

	@Override
	public int getTotalRecord(SearchVO searchVo) {		
		return reBoardDao.getTotalRecord(searchVo);
	}
	
	public ReBoardVO selectByNo(int no){
		return reBoardDao.selectByNo(no);
	}
	
	public int updateReadCount(int no){
		return reBoardDao.updateReadCount(no);		
	}
	
	public int updateReBoard(ReBoardVO vo){
		return reBoardDao.updateReBoard(vo);	
	}
	
	public boolean checkPwd(int no, String pwd){
		String dbPwd=reBoardDao.selectPwd(no);
		if(dbPwd.equals(pwd)) {
			return true;
		}else {
			return false;
		}
	}
		
	public void deleteReBoard(Map<String, String> map) {
		reBoardDao.deleteReBoard(map);
	}
	
	@Transactional
	public int deleteMulti(Map<String, String[]> map) {
		return reBoardDao.deleteMulti(map);
	}

	@Override
	public int updateDowncount(int no) {
		return reBoardDao.updateDowncount(no);
	}

	@Override
	@Transactional
	public int reply(ReBoardVO vo) {
		int cnt=reBoardDao.updateSortNo(vo.getNo()) ;
		cnt=reBoardDao.reply(vo);
		
		return cnt;
	}
	
	
	/*public List<ReBoardVO> mainNotice() throws SQLException{
		return reBoardDao.mainNotice();
	}
	
*/	
	
}





