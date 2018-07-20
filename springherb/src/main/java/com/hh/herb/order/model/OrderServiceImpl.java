package com.hh.herb.order.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.herb.cart.model.CartDAO;
import com.hh.herb.common.DateSearchVO;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private CartDAO cartDao;

	@Override
	@Transactional
	public int insertOrder(OrderVO orderVo) {
		int cnt = 0;
		// [1] orders 테이블 insert
		cnt = orderDao.insertOrder(orderVo);

		// [2] 방금 insert된 orderNo를 이용하여 orderDetails 테이블 insert
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderVo.getOrderNo());
		map.put("customerId", orderVo.getCustomerId());

		cnt = orderDao.insertOrderDetail(map);

		// [3] 해당 고객의 cart 테이블 삭제
		cnt = cartDao.deleteCartByUserid(orderVo.getCustomerId());

		return cnt;
	}

	@Override
	public List<Map<String, Object>> selectOrderDetailsView(int orderNo) {
		return orderDao.selectOrderDetailsView(orderNo);
	}

	@Override
	public Map<String, Object> selectOrdersView(int orderNo) {
		return orderDao.selectOrdersView(orderNo);
	}

	@Override
	public List<OrderAllVO> selectOrderList(DateSearchVO dateSearchVo) {
		return orderDao.selectOrderList(dateSearchVo);
	}

	@Override
	public int selectTotalRecord(DateSearchVO dateSearchVo) {
		return orderDao.selectTotalRecord(dateSearchVo);
	}

	@Override
	public List<Map<String, Object>> selectBestProduct(int productNo) {
		return orderDao.selectBestProduct(productNo);
	}

}
