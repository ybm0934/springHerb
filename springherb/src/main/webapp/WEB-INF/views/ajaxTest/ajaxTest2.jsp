<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajaxTest2.jsp</title>
<script type="text/javascript" 
	src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#bt1").click(function(){
			//[{"no":1,"name":"홍길동","content":"안녕"},{"no":2,"name":"김길동","content":"안녕하세요"},{"no":3,"name":"이길동","content":"안녕!!!"}]
			$.ajax({
				url:"<c:url value='/ajaxTest/list1.do'/>",
				type:"GET",
				dataType:"json",
				success:function(res){
					//alert("length="+ res.length);
					if(res.length>0){
						var result = "";
						$.each(res, function(idx,item){
							result+="번호:"+ item.no+"<br>";
							result+="이름:"+ item.name+"<br>";
							result+="내용:"+ item.content+"<br><br>";							
						});
						$('#div1').append(result);
					}
				},
				error:function(xhr,status,error){
					alert("error:"+error);
				}
			});
		});  //click
		
		$("#bt2").click(function(){
			//{"no":1,"name":"홍길동","content":"안녕"}
			$.ajax({
				url:"<c:url value='/ajaxTest/detail1.do'/>",
				type:"GET",
				dataType:"json",
				data:{no:"1"},
				success:function(res){
					var result="<p>번호-" + res.no+"</p>";	
					result+="<p>이름-" + res.name+"</p>";	
					result+="<p>내용-" + res.content+"</p>";
					
					$('#div2').html(result);
				},
				error:function(xhr, status, error){
					alert("error:"+error);
				}
			});
		});//click
		
		$('#bt3').click(function(){			
			$.getJSON("<c:url value='/jsonTest/memoinfo.json'/>",
				function(res){
					if(res.length>0){
						var result="";
						$.each(res, function(idx, item){
							result+="no : "+ item.no+"<br>";
							result+="name : "+ item.name+"<br>";
							result+="content : "+ item.content+"<br><br>";
						});
						$('#div2').html(result);
					}
			});
		});
		
		$('#bt4').click(function(){
			$('#div2').load("<c:url value='/ajaxTest/search.do'/>");
		});
	});
</script>	
</head>
<body>
	<h1>ajax 연습2</h1>
	<button type="button" id="bt1">메모 리스트</button>
	<button type="button" id="bt2">1번 메모</button>
	<button type="button" id="bt3">json문서 요청</button>
	<button type="button" id="bt4">load()이용</button>
	
	<div id="div1"></div>
	<div id="div2"></div>
	
</body>
</html>




