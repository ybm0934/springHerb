<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>best5.jsp</title>
<style type="text/css">
dl.dlBest{
	background: whitesmoke;
}

dt {
	padding: 7px 5px 5px 10px;
	font-weight: bold;
}

dd {
	display: block;
	padding: 0 10px;
}

dd a {
	text-decoration: none;
	display: block;
	color: #585721;
	border-bottom: 1px solid #ddd;
	padding: 7px 5px;
}
</style>
</head>
<body>
	<c:if test="${!empty list}">
		<dl class="dlBest">
			<dt>Best of Best</dt>
			<!-- 반복 시작 -->
			<c:forEach var="map" items="${list}">
				<dd>
					<a href="<c:url value='/shop/product/productDetail.do?productNo=${map["PRODUCTNO"]}'/>">
					${map['PRODUCTNAME']}
					</a>
				</dd>
			</c:forEach>
			<!-- 반복 끝 -->
		</dl>
	</c:if>
</body>
</html>