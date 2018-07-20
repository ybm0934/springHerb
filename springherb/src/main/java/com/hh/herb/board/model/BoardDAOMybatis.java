package com.hh.herb.board.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hh.herb.common.SearchVO;

@Repository
public class BoardDAOMybatis implements BoardDAO{
	private String namespace="config.mybatis.mapper.oracle.board.";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int insertBoard(BoardVO vo) {
		//글쓰기
		int cnt=sqlSession.insert(namespace+"insertBoard", vo);			
		return cnt;		
	}

	public List<BoardVO> selectAll(SearchVO searchVo){
		//글 전체 조회-글 목록
		List<BoardVO> list
		=sqlSession.selectList(namespace+"selectAll", searchVo);
		return list;

	}	

	public BoardVO selectByNo(int no){
		//no에 해당하는 글 조회
		BoardVO vo = sqlSession.selectOne(namespace+"selectByNo", no);
		return vo;
	}

	public int updateReadCount(int no) {
		//no에 해당하는 글의 조회수 증가
		int cnt=sqlSession.update(namespace+"updateReadcount", no);			
		return cnt;
	}

	public String selectPwd(int no) {
		//비밀번호 체크
		String dbPwd = sqlSession.selectOne(namespace+"selectPwd", no);
		return dbPwd;
	}

	public int updateBoard(BoardVO vo) {
		//글수정
		int cnt =sqlSession.update(namespace+"updateBoard", vo);
		return cnt;
	}

	public int deleteBoard(BoardVO vo) {
		//글 삭제
		int cnt =sqlSession.update(namespace+"deleteBoard", vo.getNo());
		return cnt;
	}

	public int deleteMulti(Map<String, String[]> map) {
		int cnt=sqlSession.delete(namespace+"deleteMulti", map);
		return cnt;
	}

	@Override
	public int getTotalRecord(SearchVO searchVo) {
		return sqlSession.selectOne(namespace+"getTotalRecord", searchVo);
	}

	@Override
	public List<BoardVO> selectMainNotice() {
		return sqlSession.selectList(namespace+"selectMainNotice");
	}


}//class





