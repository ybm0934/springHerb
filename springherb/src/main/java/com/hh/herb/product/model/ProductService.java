package com.hh.herb.product.model;

import java.util.List;

public interface ProductService {
	public List<ProductVO> selectEventProduct(String eventName);
	public ProductVO selectProduct(int productNo);
	public int insertProduct(ProductVO vo);
	public List<ProductVO> selectAll(EventProductVO searchVo);
	public int selectTotalRecord(EventProductVO searchVo);
	public int deleteMulti(List<ProductVO> list);
	public int eventMulti(List<ProductVO> list, String eventName);
}




