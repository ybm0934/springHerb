package com.hh.herb.order.model;

import java.util.List;
import java.util.Map;

public class OrderAllVO {
	private OrderVO orderVo;  //mybatis - 1:1 관계를 처리하는 association 엘리먼트 이용
	private List<Map<String, Object>> orderDetailsList;  
		//=> mybatis - 1:N 관계를 처리하는 collection 엘리먼트 이용
	
	public OrderVO getOrderVo() {
		return orderVo;
	}
	public void setOrderVo(OrderVO orderVo) {
		this.orderVo = orderVo;
	}
	public List<Map<String, Object>> getOrderDetailsList() {
		return orderDetailsList;
	}
	public void setOrderDetailsList(List<Map<String, Object>> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}
	
	@Override
	public String toString() {
		return "OrderAllVO [orderVo=" + orderVo + ", orderDetailsList=" + orderDetailsList + "]";
	}
	
	
}
