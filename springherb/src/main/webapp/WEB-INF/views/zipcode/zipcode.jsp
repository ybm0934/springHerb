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
	#divPage{
		width:470px;
		text-align: center;
		margin: 10px;
	}
</style>
<script type="text/javascript" 
	src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" 
	src="<c:url value='/js/paging.js'/>"></script>
<script type="text/javascript">
	var totalCount=0;
	
	$(document).ready(function(){
		$('#dong').focus();
		
		$('form[name=frmZip]').submit(function(){
			if($('#dong').val()==''){
				alert("지역명을 입력하세요");
				$('#dong').focus();
				return false;
			}
			
			send(1);
			
			event.preventDefault();
		});
	});

	function send(curPage){
		$('#currentPage').val(curPage);
		
		$.ajax({
			url:"<c:url value='/sample/getAddrApi.do'/>",
			type:"post",
			dataType:"xml",
			data:$("#frmZip").serializeArray(),
			success:function(res){
				var errorCode=$(res).find('errorCode').text();
				var errorMessage=$(res).find('errorMessage').text();
				if(errorCode!=0){
					alert(errorCode+"=>"+ errorMessage);
				}else{
					if(res!=null){
						makeList(res);
						applyLink();
					}
				}
			},
			error:function(xhr,status,error){
				alert("error:"+ error);
			}
		});	
	}
	
	function makeList(res){
		$('#divPage').html("");
		
		totalCount=$(res).find("totalCount").text();
		if(totalCount>0){
			$('#divCount').html("<p>도로명 주소 검색결과 ("+totalCount+" 건)</p>");
			
			  var result="<table class='box2' style='width:470px'>";
			result+="<tr><th style='width:20%'>우편번호</th><th style='width:80%'>주소</th></tr>";
			
			$(res).find("juso").each(function(idx, item){
				result+="<tr>";
				result+="<td>"+ $(item).find("zipNo").text()+"</td>";
				result+="<td><a href='#'>"
					+ $(item).find("roadAddr").text()+"</a></td>";			
				result+="</tr>";	
			});
			result+="</table>";
			$("#divList").html(result);
			
			//페이징 처리
			$.pagingProc();
		}else{
			$("#divList").html("<p>해당하는 주소가 없습니다.</p>");
		}		
	}
	
	$.pagingProc=function(){
		//페이징 처리
		var blockSize=10;
		pagination($('#currentPage').val(), $('#countPerPage').val(),
			blockSize, totalCount);
		
		//이전 블럭으로 이동
		if(firstPage>1){
			var prevBlock=$("<a href='#'></a>")
				.html('<img alt="이전 블럭으로 이동" src="../images/first.JPG">')
				.attr("onclick", "send("+(firstPage-1)+")");
			
			$("#divPage").append(prevBlock);
		}//if
		
		//[1][2][3][4][5][6][7][8][9][10]		
		var pageEl="";
		for(var i=firstPage;i<=lastPage;i++){
			if (i==currentPage){
				pageEl
=$("<span style='color: blue;font-weight: bold;font-size:1.0em'></span>").text(i);						
			}else{
				pageEl
	=$("<a href='#'></a>").html("["+i+"]").attr("onclick", "send("+i+")");					
			}
			
			$('#divPage').append(pageEl);
		}//for
		
		//다음 블럭으로 이동
		if(lastPage<totalPage){
			var nextBlock=$("<a href='#'></a>")
				.html('<img alt="다음 블럭으로 이동" src="../images/last.JPG">')
				.click(function(){
					send(lastPage+1);
				});
				//.attr("onclick","send("+ (lastPage+1)+")");
			
			$("#divPage").append(nextBlock);
		}//if
	}
	
	function applyLink(){
		/* $("#divList table td a").each(function(idx, item){
			$(this).attr("onclick", "setZipcode('"+$(this).parent().prev().text()+"','"+ $(this).text()+"')");
		}); */
		
 		$("#divList table td a").click(function(){
			setZipcode($(this).parent().prev().text(), $(this).text());				
		}); 
		
	}
	
	function setZipcode(zipcode, address){
		//opener.frm1.zipcode.value=zipcode;
		//opener.frm1.address.value=address;
		$(opener.document).find("#zipcode").val(zipcode);
		$(opener.document).find("#address").val(address);

		self.close();
	}
	
	
</script>
</head>
<body>

	<h1>도로명 주소 검색</h1>
	<p>도로명 주소, 건물명 또는 지번을 입력하세요</p>
	<p style="color:blue;font-weight: bold">
		검색어 예: 도로명 (반포대로 58), 건물명(독립기념관), 지번(삼성동 25)
	</p>
	
	<form name="frmZip" id="frmZip" method="post" 
		action="<c:url value='/zipcode/zipcode.do'/>">
		<input type="hidden" name="currentPage" id="currentPage" value="1"/>				<!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
  		<input type="hidden" name="countPerPage" id="countPerPage" value="10"/>				<!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
  		<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDE3MTIxODE3Mzc0MTEwNzU1Njg="/>		<!-- 요청 변수 설정 (승인키) -->
		
		<label for="dong">지역명</label>
		<input type="text" name="keyword" id="dong" value='${param.keyword }'>
		<input type="submit" value="찾기">		
	</form>
	<br>
	<div id="divCount"></div>
	<div id="divList"></div>
	<div id="divPage"></div>
	
	
	<%-- 
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
					<a href
="<c:url value='/zipcode/zipcode.do?currentPage=${i}&dong=${param.dong}'/>">
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
	</c:if>		 --%>
	
	
</body>
</html>









