<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>	<!-- 안되면 utf는 대문자를 써라 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%			
	//3.결과 처리
	/* String content=vo.getContent();	
	if(content!=null && !content.isEmpty()){
		//  "\r\n"	=> <br>
		//public String replace(CharSequence target, CharSequence replacement)
		content=content.replace("\r\n", "<br>");
	}else{
		content="";
	} */
	
%>    
<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>자료실 상세보기 - 허브몰</title>
<link rel="stylesheet" type="text/css" href="../css/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="../css/clear.css" />
<link rel="stylesheet" type="text/css" href="../css/formLayout.css" />
<link rel="stylesheet" type="text/css" href="../css/mystyle.css" />
<style type="text/css">
	body{
		padding:5px;
		margin:5px;
	 }
	.divForm {
		width: 500px;
		}
</style>  
</head>
<body>
	<% pageContext.setAttribute("newLine", "\r\n"); %>
	<c:set var="content" value="${fn:replace(vo.content, newLine, '<br>') }" />
	<%-- <c:set var="content" value="${fn:replace(vo.content, '\r\n', '<br>') }" /> --%>
	
	<h2>글 상세보기</h2>
	<div class="divForm">
		<div class="firstDiv">
			<span class="sp1">제목</span> <span>${vo.title}</span>
		</div>
		<div>
			<span class="sp1">작성자</span> <span>${vo.name}</span>
		</div>
		<div>
			<span class="sp1">등록일</span> <span>${vo.regdate}</span>
		</div>
		<div>
			<span class="sp1">조회수</span> <span>${vo.readcount}</span>
		</div>
		<div>
			<span class="sp1">첨부파일</span> 
			<span>
				<c:if test="${!empty vo.fileName }">
					<a href
	="<c:url value='/reBoard/download.do?no=${vo.no}&fileName=${vo.fileName }'/>">
						${fileInfo }</a>
					<span style="color:blue">다운로드수 : ${downInfo }</span>
				</c:if>
			</span>
		</div>
		<div class="lastDiv">			
			<p class="content">${content}</p>
		</div>
		<div class="center">
			<a href='<c:url value="/reBoard/edit.do?no=${param.no}"/>'>수정</a> |
        	<a href
    ='<c:url value="/reBoard/delete.do?no=${param.no}&fileName=${vo.fileName}"/>'>
    			삭제</a> |
			<a href='<c:url value="/reBoard/reply.do?no=${param.no}"/>'>답변</a> |
        	<a href='<c:url value="/reBoard/list.do"/>'>목록</a>			
		</div>
	</div>

	
</body>
</html>










