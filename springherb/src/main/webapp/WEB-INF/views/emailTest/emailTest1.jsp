<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>emailTest/emailTest1.jsp</title>
</head>
<body>
<h1>이메일 발송 연습</h1>
<hr>
<a href="<c:url value='/emailTest/send.do'/>">email 발송</a>

</body>
</html>