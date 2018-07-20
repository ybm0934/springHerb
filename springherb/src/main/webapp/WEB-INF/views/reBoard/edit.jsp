<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
<title>자료실 글 수정 - 허브몰</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="../css/clear.css" />
<link rel="stylesheet" type="text/css" href="../css/formLayout.css" />
<link rel="stylesheet" type="text/css" href="../css/mystyle.css" />
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('form[name=frmEdit]').submit(function(event){
			if($("#title").val()==""){
				alert('제목을 입력하세요');
				$("#title").focus();
				event.preventDefault();
			}else if($("#name").val().length<1){
				alert('이름을 입력하세요');
				$("#name").focus();
				return false;
			}else if(!$("#pwd").val()){
				alert('비밀번호를 입력하세요');
				$("#pwd").focus();
				return false;
			}
		});	
	});
</script>

</head>
<body>
<div class="divForm">
<form name="frmEdit" method="post" enctype="multipart/form-data"
	action="<c:url value='/reBoard/edit.do'/>"> 
    <fieldset>
	<legend>글수정</legend>
	<input type="hidden" name="no" value="${vo.no}" />
	<input type="text" name="oldFileName" value="${vo.fileName}" />
            	
        <div class="firstDiv">
            <label for="title">제목</label>
            <input type="text" id="title" name="title"  
            	value="${vo.title}" />
        </div>
        <div>
            <label for="name">작성자</label>
            <input type="text" id="name" name="name" value="${vo.name}"/>
        </div>
        <div>
            <label for="pwd">비밀번호</label>
            <input type="password" id="pwd" name="pwd" />
        </div>
        <div>
            <label for="email">이메일</label>
            <input type="text" id="email" name="email" value="${vo.email}"/>
        </div>
        <div>
            <label for="upfile">첨부파일</label>
            <input type="file" id="upfile" name="upfile" />
        </div>
        <div>
            <span class="sp1">첨부파일 목록</span>
            <c:if test="${!empty vo.fileName }">
            	<span>${fileInfo }</span>
            	<br>
            	<span style="color:green;font-weight: bold">
            		첨부파일을 새로 지정할 경우 기존 파일은 삭제됩니다.</span>
            </c:if>
        </div>
        <div>  
        	<label for="content">내용</label>        
 			<textarea id="content" name="content" rows="12" cols="40">${vo.content}</textarea>
        </div>
        <div class="center">
            <input type = "submit" value="수정"/>
            <input type = "Button" value="글목록" 
            	onclick="location.href='<c:url value='/reBoard/list.do'/>'" />         
        </div>
	</fieldset>
</form>    
</div>

</body>
</html>