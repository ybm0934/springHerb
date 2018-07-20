package com.hh.herb.order.model;

import java.util.List;
import java.util.Map;

import com.hh.herb.common.DateSearchVO;

public interface OrderDAO {
	public int insertOrder(OrderVO orderVo);
	public int insertOrderDetail(Map<String, Object> map);
	public List<Map<String, Object>> selectOrderDetailsView(int orderNo);
	public Map<String, Object> selectOrdersView(int orderNo);
	public List<OrderAllVO> selectOrderList(DateSearchVO dateSearchVo);
	public int selectTotalRecord(DateSearchVO dateSearchVo);
	public List<Map<String, Object>> selectBestProduct(int productNo);
	
}
