<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<style type="text/css">
	#divWrap{
		width: 780px;
	}
	#divPd{
		width:140px;
		float: left;
		padding: 20px;
		margin: 0 10px 10px 0;
		border: 1px solid silver;
		text-align: center;
	}
	.line{
		padding: 20px;
		border: 1px solid silver;
	}
</style>

<div id="divWrap">
	<c:if test="${empty list }">
		<div class="line">
			해당 이벤트의 상품이 없습니다.
		</div>
	</c:if>
	<!-- 반복시작 -->
	<c:forEach  var="vo" items="${list }">
		<div id="divPd">
			<a href
	="<c:url value='/shop/product/productDetail.do?productNo=${vo.productNo}'/>">
				<img src="<c:url value='/pd_images/${vo.imageURL }'/>" 
					alt="${vo.productName }">
			</a>	
			<br>
			${vo.productName }
			<br>
			<fmt:formatNumber value="${vo.sellPrice }" pattern="#,###"/>원
		</div>		
	</c:forEach>
	<!-- 반복 끝 -->
</div>


