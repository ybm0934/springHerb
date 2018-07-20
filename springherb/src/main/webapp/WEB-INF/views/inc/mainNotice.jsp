<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
	.divNotice{
		width:310px;
	}
	.divNotice span{
		margin:0 0 0 160px;
	}
	.img1{
		width:310px;
		height: 6px;
	}
	.divNotice table{
		width: 300px;
	}
</style>

<div class="divNotice">
	<div>
		<img src="${pageContext.request.contextPath}/images/notice2.JPG" 
			alt="공지사항 이미지">
		<span>
			<a href="<c:url value='/board/list.do'/>">
				<img src="${pageContext.request.contextPath}/images/more.JPG" 
					alt="more">
			</a>
		</span>	
	</div>
	<div>
		<img src="${pageContext.request.contextPath}/images/Line.JPG" class="img1">
	</div>
	<div>
		<table>
			<!-- 반복시작 -->
			<c:forEach var="vo" items="${list }">			
				<tr>
					<td>
						<img src="${pageContext.request.contextPath}/images/dot.JPG">
						<a href
		="<c:url value='/board/detail.do?no=${vo.no}'/>">
							<c:if test="${fn:length(vo.title)>20}">
								${fn:substring(vo.title, 0,20)}...
							</c:if>
							<c:if test="${fn:length(vo.title)<=20}">
								${vo.title}
							</c:if>	
						</a>
					</td>
				</tr>
			</c:forEach>
			<!-- 반복 끝 -->
		</table>
	</div>
</div>





