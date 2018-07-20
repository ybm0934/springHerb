package com.hh.herb.product.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired private ProductDAO productDao;
	
	@Override
	public List<ProductVO> selectEventProduct(String eventName) {
		return productDao.selectEventProduct(eventName);
	}

	@Override
	public ProductVO selectProduct(int productNo) {
		return productDao.selectProduct(productNo);
	}

	@Override
	public int insertProduct(ProductVO vo) {
		return productDao.insertProduct(vo);
	}
		
	@Override
	public List<ProductVO> selectAll(EventProductVO searchVo) {
		return productDao.selectAll(searchVo);
	}

	@Override
	public int selectTotalRecord(EventProductVO searchVo) {
		return productDao.selectTotalRecord(searchVo);
	}

	@Override
	@Transactional
	public int deleteMulti(List<ProductVO> list) {
		int cnt=0;
		try {
			for(ProductVO vo : list) {
				int productNo=vo.getProductNo();
				if(productNo>0) {  //선택한 상품만 삭제
					cnt=productDao.deleteProduct(productNo);
				}
			}//for
		}catch(RuntimeException e) {
			cnt=-1;
			e.printStackTrace();
		}
		
		return cnt;
	}

	@Override
	@Transactional
	public int eventMulti(List<ProductVO> list, String eventName) {
		int cnt=0;
		
		try {
			for(ProductVO vo : list) {
				if(vo.getProductNo()>0) {
					EventProductVO eventVo = new EventProductVO();
					eventVo.setProductNo(vo.getProductNo());
					eventVo.setEventName(eventName);
					
					int count=productDao.selectEventCount(eventVo);
					if(count>0) {
						//이미 해당 상품이 해당 이벤트로 등록된 경우
						cnt=1;  //성공으로 간주, skip
					}else {
						cnt=productDao.insertEventProduct(eventVo);
					}
				}
			}//for
		}catch(RuntimeException e) {
			cnt=-1;   //rollback시 에러 처리를 위해 cnt값을 -1로 셋팅
			e.printStackTrace();
		}
		
		return cnt;
	}
	

}






