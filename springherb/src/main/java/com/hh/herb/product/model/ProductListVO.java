package com.hh.herb.product.model;

import java.util.List;
//커맨드 객체로 List받기
public class ProductListVO {
	private List<ProductVO> pdItems;

	public List<ProductVO> getPdItems() {
		return pdItems;
	}

	public void setPdItems(List<ProductVO> pdItems) {
		this.pdItems = pdItems;
	}

	@Override
	public String toString() {
		return "ProductListVO [pdItems=" + pdItems + "]";
	}
	
	
}
