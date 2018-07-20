package com.hh.herb.reboard.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hh.herb.common.SearchVO;

@Repository
public class ReBoardDAOMybatis implements ReBoardDAO{
	private String namespace="config.mybatis.mapper.oracle.reBoard.";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int insertReBoard(ReBoardVO vo) {
		//글쓰기
		int cnt=sqlSession.insert(namespace+"insertReBoard", vo);			
		return cnt;		
	}

	public List<ReBoardVO> selectAll(SearchVO searchVo){
		//글 전체 조회-글 목록
		List<ReBoardVO> list
		=sqlSession.selectList(namespace+"selectAll", searchVo);
		return list;

	}	

	public ReBoardVO selectByNo(int no){
		//no에 해당하는 글 조회
		ReBoardVO vo = sqlSession.selectOne(namespace+"selectByNo", no);
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

	public int updateReBoard(ReBoardVO vo) {
		//글수정
		int cnt =sqlSession.update(namespace+"updateReBoard", vo);
		return cnt;
	}

	public void deleteReBoard(Map<String, String> map) {
		//글 삭제
		sqlSession.delete(namespace+"deleteReBoard", map);		
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
	public int updateDowncount(int no) {
		return sqlSession.update(namespace+"updateDowncount", no);
	}

	@Override
	public int updateSortNo(int no) {
		return sqlSession.update(namespace+"updateSortNo", no);
	}

	@Override
	public int reply(ReBoardVO vo) {
		return sqlSession.insert(namespace+"reply", vo);
	}


	/*
	public List<ReBoardVO> mainNotice() throws SQLException{
		//최근 공지사항 6개 조회
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<ReBoardVO> list=new ArrayList<ReBoardVO>();
		try {
			conn=pool.getConnection();

			String sql="select no, title" + 
					" from(" + 
					"    select no, title from reBoard order by no desc" + 
					" )" + 
					" where rownum<=6";
			ps=conn.prepareStatement(sql);

			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				String title=rs.getString("title");

				ReBoardVO vo = new ReBoardVO();
				vo.setNo(no);
				vo.setTitle(title);

				list.add(vo);
			}
			System.out.println("메인 공지사항 조회 결과 list.size="+list.size());
		}finally {
			pool.dbClose(rs, ps, conn);
		}
		return list;
	}


	 */
}//class





