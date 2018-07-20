<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>우편번호 찾기 - 허브몰</title>
<link href="<c:url value='/css/mainstyle.css'/>" rel="stylesheet" type="text/css">
<style type="text/css">
	.divPage{
		width:470px;
		text-align: center;
		margin-top: 10px;
	}
</style>
<script type="text/javascript" 
	src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#dong').focus();
		
		$('form[name=frmZip]').submit(function(){
			if($('#dong').val()==''){
				alert("지역명을 입력하세요");
				$('#dong').focus();
				return false;
			}
		});
	});

	function setZipcode(zipcode, address){
		//opener.frm1.zipcode.value=zipcode;
		//opener.frm1.address.value=address;
		$(opener.document).find("#zipcode").val(zipcode);
		$(opener.document).find("#address").val(address);

		self.close();
	}
	
	function pageFunc(curPage){
		//document.frmPage.currentPage.value=curPage;
		//document.frmPage.submit();
		$('form[name=frmPage] input[name=currentPage]').val(curPage);
		$('form[name=frmPage]').submit();
	}
</script>
</head>
<body>
<!-- zipcode.do?currentPage=6&dong=개포 -->

<form name="frmPage" method="post" 
	action="<c:url value='/zipcode/zipcode.do'/>">
	<input type="hidden" name="currentPage">
	<input type="hidden" name="dong" value="${param.dong}">
</form>

	<h1>우편번호 검색</h1>
	<p>찾고 싶으신 주소의 동(읍, 면)을 입력하세요</p>
	<form name="frmZip" method="post" 
		action="<c:url value='/zipcode/zipcode.do'/>">
		<label for="dong">지역명</label>
		<input type="text" name="dong" id="dong" value='${param.dong }'>
		<input type="submit" value="찾기">		
	</form>
	<br>
	<c:if test="${empty list && !empty param.dong}">
		<p>해당하는 데이터가 없습니다.</p>
	</c:if>
	<c:if test="${!empty list}">
		<table class="box2" style="width:470px" 
	summary="우편번호 검색 결과에 관한 표로써, 우편번호, 주소에 대한 정보를 제공합니다.">
			<colgroup>
				<col style="width:20%">
				<col style="width:80%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">우편번호</th>
					<th scope="col">주소</th>
				</tr>
			</thead>
			<tbody>				
				<c:if test="${!empty list }">				
					<!-- 반복시작 -->					
					<c:forEach var="vo" items="${list }">
						<c:set var="address" 
							value="${vo.sido} ${vo.gugun} ${vo.dong }" />
						<c:set var="bunji" value="${vo.startbunji }"/>
						<c:if test="${!empty vo.endbunji }">
							<c:set var="bunji" 
								value="${bunji } ~ ${vo.endbunji }"/>
						</c:if>
						
						<tr>
							<td>${vo.zipcode}</td>
							<td>
							<a href="#" 
					onclick="setZipcode('${vo.zipcode}','${address}')">
								${address} ${bunji}
							</a>
							</td>							
						</tr>					
					</c:forEach>
					<!-- 반복끝 -->
				</c:if>
			</tbody>
		</table>
		
		<div class="divPage">
			<!-- 페이지 번호 추가 -->		
			<!-- 이전 블럭으로 이동 -->
			<c:if test="${pagingInfo.firstPage>1 }">
				<a href="#" onclick="pageFunc(${pagingInfo.firstPage-1})">	
					<img alt="이전 블럭으로 이동" src="../images/first.JPG">
				</a>	
			</c:if>			
			
			<!-- [1][2][3][4][5][6][7][8][9][10] -->
			<c:forEach var="i" begin="${pagingInfo.firstPage}" 
				end="${pagingInfo.lastPage}">
				<c:if test="${i==pagingInfo.currentPage}">
					<span style="color: blue;font-weight: bold;font-size:1.0em">
						${i }</span>
				</c:if>
				<c:if test="${i!=pagingInfo.currentPage}">
					<%-- <a href
="<c:url value='/zipcode/zipcode.do?currentPage=${i}&dong=${param.dong}'/>"> --%>
					<a href="#" onclick="pageFunc(${i})">
						[${i }]</a>
				</c:if>			
			</c:forEach>
				
			<!-- 다음 블럭으로 이동 -->
			<c:if test="${pagingInfo.lastPage<pagingInfo.totalPage }">
				<a href="#" onclick="pageFunc(${pagingInfo.lastPage+1})">	
					<img alt="다음 블럭으로 이동" src="../images/last.JPG">
				</a>	
			</c:if>
			<!--  페이지 번호 끝 -->
		</div>
	</c:if>		
</body>
</html>









