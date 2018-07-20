<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="inc/top.jsp"%>
<article id="centerCon">
	<img src="${pageContext.request.contextPath}/images/herb.JPG" border="0"
		class="mainImg" /><br />
	<br />
</article>
<article id="rightCon">
	<c:import url="/inc/mainNotice.do"></c:import>
</article>

<!-- 상품 목록 include -->
<article id="listCon">
	<c:import url="shop/product/productCatalog.jsp"></c:import>
</article>

<%@ include file="inc/bottom.jsp" %>