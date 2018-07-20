package com.hh.herb.reboard.model;

import java.util.List;
import java.util.Map;

import com.hh.herb.common.SearchVO;

public interface ReBoardDAO {
	public int insertReBoard(ReBoardVO vo) ;
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	public int getTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public String selectPwd(int no);
	public int updateReBoard(ReBoardVO vo);
	public void deleteReBoard(Map<String, String> map);
	public int deleteMulti(Map<String, String[]> map);
	public int updateDowncount(int no);
	public int updateSortNo(int no);
	public int reply(ReBoardVO vo);
	
}
