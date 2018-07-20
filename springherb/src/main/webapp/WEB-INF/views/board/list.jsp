<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML>
<html lang="ko">
<head>
<title>자유게시판 글 목록 - 허브몰</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="../css/clear.css" />
<link rel="stylesheet" type="text/css" href="../css/formLayout.css" />
<link rel="stylesheet" type="text/css" href="../css/mystyle.css" />
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">	
	$(document).ready(function(){
		$('table.box2 tbody tr').hover(function(){
			$(this).css('background','skyblue');
		}, function(){
			$(this).css('background','');
		});	
		
		$('form[name=frmDel]').submit(function(){
			var count=0;
			$('input[name=chk]').each(function(idx, item){
				if($(this).is(':checked')){
					count++;
				}
			});	
			
			if(count==0){
				alert('삭제할 글을 체크하세요');
				return false;
			}
		});
		
	});
	
	function pageFunc(curPage){
		document.frmPage.currentPage.value=curPage;
		frmPage.submit();
	}
	
	function allChecked(bool){
		$('input[name=chk]').prop('checked', bool);
	}
	
</script>
<style type="text/css">
	body{
		padding:5px;
		margin:5px;
	 }	
</style>	
</head>	
<body>
<h2>자유게시판</h2>
<c:if test="${!empty param.searchKeyword}">
	<p>검색어 : ${param.searchKeyword}, ${fn:length(list)}건 검색되었습니다. </p> 
</c:if>
<c:if test="${empty param.searchKeyword}">
	<p>전체 조회 : ${pageVo.totalRecord}건 조회되었습니다. </p> 
</c:if>

<!-- 페이징 처리를 위한 form -->
<form name="frmPage" method="post"
	action="<c:url value='/board/list.do'/>">
	<input type="text" name="currentPage" >
	<input type="text" name="searchKeyword" value="${param.searchKeyword}">
	<input type="text" name="searchCondition" value="${param.searchCondition}">	
</form>

<div class="divList">
<form name="frmDel" method="post" 
	action="<c:url value='/board/deleteMulti.do'/>">
<table class="box2"
	 	summary="기본 게시판에 관한 표로써, 번호, 제목, 작성자, 작성일, 조회수에 대한 정보를 제공합니다.">
	<caption>기본 게시판</caption>
	<colgroup>
		<col style="width:3%;" />
		<col style="width:10%;" />
		<col style="width:50%;" />
		<col style="width:15%;" />
		<col style="width:15%;" />
		<col style="width:*;" />		
	</colgroup>
	<thead>
	  <tr>
	    <th scope="col"><input type="checkbox" name="chkAll" 
	    	onclick="allChecked(this.checked)"></th>
	    <th scope="col">번호</th>
	    <th scope="col">제목</th>
	    <th scope="col">작성자</th>
	    <th scope="col">작성일</th>
	    <th scope="col">조회수</th>
	  </tr>
	</thead> 
	<tbody>  
		<c:if test="${empty list }">
			<tr>
				<td colspan="6" style="text-align: center">
					데이터가 존재하지 않습니다.
				</td>
			</tr>
		</c:if>
		<c:if test="${!empty list }">
		  	<!--게시판 내용 반복문 시작  -->	
		  	<c:forEach var="vo" items="${list}">
	  			<tr  style="text-align:center">
					<td>
						<input type="checkbox" name="chk" value="${vo.no}">
					</td>
					<td>${vo.no}</td>
					<td style="text-align:left">
						<a href
					="<c:url value='/board/countUpdate.do?no=${vo.no}'/>">
							<!-- 제목이 긴 경우 일부만 보여주기 -->
							<c:if test="${fn:length(vo.title)>23}">	
								${fn:substring(vo.title, 0, 23)}...
							</c:if>
							<c:if test="${fn:length(vo.title)<=23}">
								${vo.title}
							</c:if>								
						</a>
						<!-- 24시간 이내의 글인 경우 new 이미지 보여주기 -->
						<c:if test="${vo.newImgTerm<24 }">
							<img src="<c:url value='/images/new.gif'/>" 
								alt="new 이미지">
						</c:if>
					</td>
					<td>${vo.name}</td>
					<td><fmt:formatDate value="${vo.regdate}"
						pattern="yyyy-MM-dd"/> </td>
					<td>${vo.readcount}</td>		
				</tr>
			</c:forEach>
		  	<!--반복처리 끝  -->
	  	</c:if>
	  </tbody>
</table>
<br>
<input type="submit" value="글 삭제" >
</form>	   
</div>

<div class="divPage">
	<!-- 페이지 번호 추가 -->		
	<!-- 이전 블럭으로 이동 -->
	<c:if test="${pageVo.firstPage>1 }">
		<a href="#" onclick="pageFunc(${pageVo.firstPage-1})">
			<img alt="이전 블럭으로 이동" src="../images/first.JPG">
		</a>		
	</c:if>
	
	<!-- [1][2][3][4][5][6][7][8][9][10] -->
	<c:forEach var="i" begin="${pageVo.firstPage }" end="${pageVo.lastPage}">
		<c:if test="${i==pageVo.currentPage }">
			<span style="color: blue;font-weight: bold;font-size:1.0em">
				${i}</span>
		</c:if>
		<c:if test="${i!=pageVo.currentPage }">
			<a href="#" onclick="pageFunc(${i})">
			[${i }]</a>
		</c:if>
	</c:forEach>
		
	<!-- 다음 블럭으로 이동 -->
	<c:if test="${pageVo.lastPage<pageVo.totalPage }">
		<a href="#" onclick="pageFunc(${pageVo.lastPage+1})">
			<img alt="다음 블럭으로 이동" src="../images/last.JPG">
		</a>	
	</c:if>
	<!--  페이지 번호 끝 -->
</div>
<div class="divSearch">
   	<form name="frmSearch" method="post" 
   		action='<c:url value="/board/list.do"/>'>
        <select name="searchCondition">        	
            <option value="title"
            	<c:if test="${param.searchCondition=='title'}">
            		selected="selected"
            	</c:if>	 
            >제목</option>
            <option value="content"
            	<c:if test="${param.searchCondition=='content'}">
            		selected="selected"
            	</c:if>
            >내용</option>
            <option value="name"
            	<c:if test="${param.searchCondition=='name'}">
            		selected="selected"
            	</c:if>
            >작성자</option>
        </select>   
        <input type="text" name="searchKeyword" title="검색어 입력"
        	value="${param.searchKeyword}">   
		<input type="submit" value="검색">
    </form>
</div>

<div class="divBtn">
    <a href='<c:url value="/board/write.do"/>' >글쓰기</a>
</div>

</body>
</html>

