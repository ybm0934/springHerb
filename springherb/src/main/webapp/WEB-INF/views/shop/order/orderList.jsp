<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/top.jsp" %>

<script type="text/javascript">
	function pageFunc(curPage){
		$('form[name="frmPage"]').find('input[name="currentPage"]').val(curPage);
		$('form[name=frmPage]').submit();
	}
</script>

<!-- 페이징 처리를 위한 form 시작-->
<form name="frmPage" method="post">
	<input type="hidden" name="startDay" value="${param.startDay }">
	<input type="hidden" name="endDay" value="${param.endDay }">
	<input type="hidden" name="currentPage">	
</form>
<!-- 페이징 처리 form 끝 -->

<h2>주문 내역/ 배송현황</h2><br>

<form name="frm1" method="post" action="<c:url value='/shop/order/orderList.do'/>" >
	<%@ include file="../../inc/dateTerm.jsp" %>
	
	<input type="submit" value="조회" >
</form>
<br>

<div class="divList">
<table class="box2"
	summary="주문 내역에 관한 표로써, 주문번호, 주문일자, 상품명/가격/수량,주문총금액,배송현황,취소/교환/반품에 대한 정보를 제공합니다.">
	<caption>주문 내역</caption>
	<colgroup>
		<col style="width:9%" />
		<col style="width:12%" />
		<col style="width:40%" />
		<col style="width:12%" />
		<col style="width:12%" />	
		<col style="width:14%" />
	</colgroup>
	<thead>
	  <tr>
	    <th scope="col">주문번호</th>
		<th scope="col">주문일자</th>
		<th scope="col">상품명/가격/수량</th>
		<th scope="col">주문총금액</th>
		<th scope="col">배송현황</th>
		<th scope="col">취소/교환/반품</th>
	  </tr>
	</thead> 
	<tbody>
		<c:if test="${empty list }">
			<tr>
				<td colspan="6">해당 기간의 주문내역이 없습니다.</td>
			</tr>
		</c:if>
		<c:if test="${!empty list }">		
			<!-- 반복 시작 -->
			<c:forEach var="orderAllVo" items="${list }">
				<tr class="align_center">
					<td>${orderAllVo.orderVo.orderNo }</td>
					<td><fmt:formatDate value="${orderAllVo.orderVo.orderDate }" pattern="yyyy-MM-dd"/></td>
					<td class="align_left">
						<!-- 주문 상세 내역 -->
						<c:forEach var="map" items="${orderAllVo.orderDetailsList }">
							<img src="<c:url value='/pd_images/${map["IMAGEURL"]}'/>" 
								alt="${map['PRODUCTNAME'] }" width="30" align="absmiddle">
							${map['PRODUCTNAME'] }
							<b><fmt:formatNumber value="${map['SELLPRICE'] }" pattern="#,###"/></b> 원 / 
							${map['QUANTITY'] } 개 <BR>
						</c:forEach>
					</td>
					<td class="align_right">
						<fmt:formatNumber value="${orderAllVo.orderVo.totalPrice }" pattern="#,###"/>원</td>
					<td>${orderAllVo.orderVo.deliveryStatus }</td>
					<td>취소</td>
				</tr>
			</c:forEach>
			<!-- 반복 끝 -->
		</c:if>
</tbody>
</table>
</div>

<div class="divPage">		
	<!-- 페이지 번호 추가 -->		
	<c:if test="${pagingInfo.firstPage>1 }">
		<a href="#" onclick="pageFunc(${pagingInfo.firstPage-1})">			
		    <img src='<c:url value="/images/first.JPG" />'  border="0">	</a>
	</c:if>
					
	<!-- [1][2][3][4][5][6][7][8][9][10] -->
	<c:forEach var="i" begin="${pagingInfo.firstPage }" 
	end="${pagingInfo.lastPage }">
		<c:if test="${i==pagingInfo.currentPage }">
			<span style="color:blue;font-weight:bold">${i }</span>
		</c:if>
		<c:if test="${i!=pagingInfo.currentPage }">						
			<a href="#" onclick="pageFunc(${i})">
				[${i }]
			</a>
		</c:if>
	</c:forEach>
		
	<c:if test="${pagingInfo.lastPage<pagingInfo.totalPage }">
		<a href="#" onclick="pageFunc(${pagingInfo.lastPage+1})">			
			<img src="<c:url value="/images/last.JPG" />" border="0">
		</a>
	</c:if>
	<!--  페이지 번호 끝 -->
</div>




<%@ include file="../../inc/bottom.jsp" %>