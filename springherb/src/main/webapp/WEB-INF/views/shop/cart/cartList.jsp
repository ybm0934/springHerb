<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/top.jsp" %>
<style type="text/css">
	.box2, #divBt{
		width: 600px;
	}
	
	#divBt{
		margin: 5px;
	}
	#divBt a{
		font-size: 0.9em;
	}
</style>
<script type="text/javascript">
	function send(form){
		if(form.quantity.value=='' || form.quantity.value=='0'){
			alert("수량을 입력하세요");
			form.quantity.focus();
			return false;
		}
		
		return true;
	}
</script>

<h2>장바구니</h2>
<table class="box2" summary="장바구니 목록에 관한 표로써, 상품명, 가격, 수량, 금액등의 정보를 제공합니다.">
	<colgroup>
		<col style="width:40%">
		<col style="width:13%">
		<col style="width:20%">
		<col style="width:13%">
		<col style="width:*">
	</colgroup>
	<thead>
		<tr>
			<th scope="col">상품명</th>
			<th scope="col">가격</th>
			<th scope="col">수량</th>
			<th scope="col">금액</th>
			<th scope="col">삭제</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty list }">
			<tr>
				<td colspan="5" class="align_center">장바구니가 비었습니다.</td>
			</tr>
		</c:if>
		<c:if test="${!empty list }">
			<!-- 반복 시작 -->
			<c:set var="buyPrice" value="0" />  <!-- 총구매금액 -->
			<c:set var="delivery" value="0" />  <!-- 배송비 -->
			<c:set var="orderSum" value="0" />  <!-- 총주문합계 -->
			
			<c:forEach var="map" items="${list }">
				<!-- 금액 계산 -->
				<c:set var="sum" value="${map['SELLPRICE']*map['QUANTITY']}" />
				<tr class="align_center">
					<td class="align_left"><img src="<c:url value='/pd_images/${map["IMAGEURL"] }'/>" 
							alt="${map['PRODUCTNAME'] }" align="absmiddle" width="40">
						${map["PRODUCTNAME"] }
					</td>
					<td class="align_right">
						<fmt:formatNumber value="${map['SELLPRICE']}" pattern="#,###"/>원
					</td>
					<td>
						<form name="frmCart" method="post" action="<c:url value='/shop/cart/editCart.do'/>"
							onsubmit="return send(this)">
							<input type="text" name="quantity" size="4" value="${map['QUANTITY'] }">
							<input type="hidden" name="cartNo" value="${map['CARTNO'] }">
							<input type="submit" value="수정">
						</form>
					</td>
					<td class="align_right"><fmt:formatNumber value="${sum}" pattern="#,###"/>원</td>
					<td><a href="">삭제</a></td>
				</tr>
				<!-- 총 구매금액 계산 -->
				<c:set var="buyPrice" value="${buyPrice+sum}" />
			</c:forEach>
			<!-- 반복 끝 -->
			
			<!-- 배송비 계산 - 총구매금액이 3만원 미만이면 배송비 3000원 -->
			<c:if test="${buyPrice<TOTAL_PRICE }">
				<c:set var="delivery" value="${DELIVERY }" />
			</c:if>
			
			<!-- 총 주문합계 계산 -->
			<c:set var="orderSum" value="${buyPrice+delivery }"/>
			
			<tr>
				<td colspan="4" class="align_right" style="border-right: none">
					총 구매금액 : <br> 
					   + 배송비 : <br>
					총 주문합계 : 
				</td>
				<td class="align_right" style="border-left: none">
					<fmt:formatNumber value="${buyPrice }" pattern="#,###"/> 원<br>
					<fmt:formatNumber value="${delivery }" pattern="#,###"/> 원<br>
					<fmt:formatNumber value="${orderSum }" pattern="#,###"/> 원
				</td>
			</tr>			
		</c:if>
	</tbody>
</table>
<div id="divBt" class="align_center">
	<c:if test="${!empty list }">
		<a href="<c:url value='/shop/order/orderSheet.do'/>">[ 주문하기 ]</a>
	</c:if>
	<a href="<c:url value='/index.do'/>">[ 계속 쇼핑하기 ]</a>
</div>

<%@ include file="../../inc/bottom.jsp" %>














