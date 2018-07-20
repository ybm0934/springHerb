package com.hh.herb.board.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.herb.common.SearchVO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDao;
	
	public int insertBoard(BoardVO vo) {
		return boardDao.insertBoard(vo);
	}
		
	public List<BoardVO> selectAll(SearchVO searchVo){
		return boardDao.selectAll(searchVo);
	}

	@Override
	public int getTotalRecord(SearchVO searchVo) {		
		return boardDao.getTotalRecord(searchVo);
	}
	
	public BoardVO selectByNo(int no){
		return boardDao.selectByNo(no);
	}
	
	public int updateReadCount(int no){
		return boardDao.updateReadCount(no);		
	}
	
	public int updateBoard(BoardVO vo){
		return boardDao.updateBoard(vo);	
	}
	
	public boolean checkPwd(int no, String pwd){
		String dbPwd=boardDao.selectPwd(no);
		if(dbPwd.equals(pwd)) {
			return true;
		}else {
			return false;
		}
	}
		
	public int deleteBoard(BoardVO vo) {
		return boardDao.deleteBoard(vo);
	}
	
	@Transactional
	public int deleteMulti(Map<String, String[]> map) {
		return boardDao.deleteMulti(map);
	}

	@Override
	public List<BoardVO> selectMainNotice() {
		return boardDao.selectMainNotice();
	}
	
	
	

}





