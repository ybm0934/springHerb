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
					makeEl(res);
					apply();
				},
				error:function(xhr,status,error){
					alert("error:"+error);
				}
			});
		});  //click
		
		function makeEl(res){
			if(res.length>0){
				var result = "";
				$.each(res, function(idx,item){
					result+="<p id='"+item.no+"'>번호-" + item.no+"</p>";	
					result+="<p><a href='#'>이름-" + item.name+"</a></p>";	
					result+="<p>내용-" + this.content+"</p><br>";							
				});
				$('#div1').html(result+"<hr>");
			}
		}
		
		function apply(){
			$('#div1').find('p a').click(function(){
				var id=$(this).parent().prev().attr('id');
				alert(id);
				
				$.ajax({
					url:"<c:url value='/ajaxTest/detail1.do'/>",
					type:"GET",
					dataType:"json",
					data:{no:id},
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
			});	
		}
		
	});
</script>	
</head>
<body>
	<h1>ajax 연습2</h1>
	<button type="button" id="bt1">메모 리스트</button>
	
	<div id="div1"></div>
	<div id="div2"></div>
	
</body>
</html>




