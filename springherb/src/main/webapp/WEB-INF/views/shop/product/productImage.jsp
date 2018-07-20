<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>productImage.jsp</title>
<style type="text/css">
	#bigImage{
		text-align: center;
	}
</style>
<script type="text/javascript" 
	src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$('#bigImage button').click(function(){
			self.close();
		});	
	});
</script>
</head>
<body>
<div id="bigImage">
	<img src="<c:url value='/pd_images/${param.imageURL }'/>" 
		alt="${param.productName }" width="380px">
	<button type="button">닫기</button>	
</div>

</body>
</html>
