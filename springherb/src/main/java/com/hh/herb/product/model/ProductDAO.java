package com.hh.herb.product.model;

import java.util.List;

public interface ProductDAO {
	public List<ProductVO> selectEventProduct(String eventName);
	public ProductVO selectProduct(int productNo);
	public int insertProduct(ProductVO vo);
	public List<ProductVO> selectAll(EventProductVO searchVo);
	public int selectTotalRecord(EventProductVO searchVo);
	public int deleteProduct(int productNo);
	public int selectEventCount(EventProductVO vo);
	public int insertEventProduct(EventProductVO vo);
	
}
