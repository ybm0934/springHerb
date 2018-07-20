package com.hh.herb.board.model;

import java.util.List;
import java.util.Map;

import com.hh.herb.common.SearchVO;

public interface BoardDAO {
	public int insertBoard(BoardVO vo) ;
	public List<BoardVO> selectAll(SearchVO searchVo);
	public int getTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public BoardVO selectByNo(int no);
	public String selectPwd(int no);
	public int updateBoard(BoardVO vo);
	public int deleteBoard(BoardVO vo);
	public int deleteMulti(Map<String, String[]> map);
	public List<BoardVO> selectMainNotice();
	
}
