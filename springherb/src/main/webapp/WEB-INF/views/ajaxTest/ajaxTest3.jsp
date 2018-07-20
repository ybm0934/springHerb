<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajaxTest/ajaxTest3.jsp</title>
<script type="text/javascript" 
	src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#query').click(function(){
			//{"no":5,"name":"홍길동","content":"안녕"}
			var id=$('#no').val();
			
			$.ajax({
				url:"<c:url value='/ajaxTest/detail1.do'/>",
				type:"GET",
				dataType:"json",
				data:{no:id},
				success:function(res){
					var result="<p>번호:"+res.no+"</p>";
					result+="<p>이름:"+res.name+"</p>";
					result+="<p>메모:"+res.content+"</p>";
					
					$('#result').html(result);
				},
				error:function(xhr,status, error){
					alert("error:"+error);
				}
			});	
		});//click
		
		$('#frm1').submit(function(){
			//{"message":"등록 성공","result":{"no":10,"name":"김길동","content":"안녕"}}
			
			//var p_name=$('#name').val();
			//var p_content=$('#content').val();
			alert($.param($(this).serializeArray()));
			
			$.ajax({				
				url:"<c:url value='/ajaxTest/memoWrite.do'/>",
				type:"post",
				dataType:"json",
				/* data:{name:p_name,
					  content:p_content}, */
				data:$(this).serializeArray(),	  
				//data:$(this).serialize(),	  
				success:function(res){
					var result="["+res.message+"]<br>번호:"+ res.result.no+"<br>";
					result+="이름:"+ res.result.name+"<br>";
					result+="내용:"+ res.result.content+"<br>";
					
					$("#result").html(result);
				},
				error:function(xhr, status, error){
					alert("error:"+error);
				}
			});
			
			event.preventDefault();
		});
		
	});
	
</script>	
</head>
<body>
<h1>ajax 연습3</h1>
<form id="frm1">
	 번호 : <input type="text" id="no" size="7"/>
	 <input type="button" id="query" value="조회"><br>
	
	 <h2>메모를 남기세요</h2>
	 이름 : <input type="text" id="name" name="name"/><br>
	 메모 : <input type="text" id="content" name="content" size="50" /><br>
	 <input type="submit" value="입력">
	     
	 <h2>결과</h2>
	 <div id="result" style="background:#eeeeee;width:500px"></div>
</form>	
</body>
</html>