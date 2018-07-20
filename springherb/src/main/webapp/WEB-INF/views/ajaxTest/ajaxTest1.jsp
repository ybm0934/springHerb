<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajaxTest/ajaxTest1.jsp</title>
<script type="text/javascript" 
	src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//sbs,sk,sm
		$.ajax({
			url:"<c:url value='/ajaxTest/search.do'/>",
			type:"GET",
			//data:"id=hong&keyword=s",
			data:{id:"hong",
				  keyword:"s"},
			success:function(res){
				$('#div1').append(res);				
			},
			error:function(xhr, status, error){
				alert('error:'+ error+", status="+status);
			}
		});
	});
	
</script>
</head>
<body>
<h1>ajax 연습1</h1>
<div id="div1"></div>

</body>
</html>








