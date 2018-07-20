<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="vo" items="${list }">
    <dd>
    	<a href
   ="<c:url value='/product/productByCategory.do?categoryNo=${vo.categoryNo}&categoryName=${vo.categoryName }'/>">
    	${vo.categoryName }</a>
    </dd>
</c:forEach>
