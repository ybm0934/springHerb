<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="../css/clear.css" />
<link rel="stylesheet" type="text/css" href="../css/formLayout.css" />
<link rel="stylesheet" type="text/css" href="../css/mystyle.css" />

<title>자료실 글쓰기 - 허브몰</title>
<script type="text/javascript">
	function send(form){
		if(form.title.value==""){
			alert('제목을 입력하세요');
			form.title.focus();
			return false;
		}else if(form.name.value.length<1){
			alert('이름을 입력하세요');
			form.name.focus();
			return false;
		}else if(!form.pwd.value){
			alert('비밀번호를 입력하세요');
			form.pwd.focus();
			return false;
		}
		
		return true;
	}
</script>

</head>
<body>
<div class="divForm">
<form name="frmWrite" method="post" enctype="multipart/form-data"
	action="<c:url value='/reBoard/write.do'/>" 
	onsubmit="return send(this)">
 <fieldset>
	<legend>글쓰기</legend>
        <div class="firstDiv">
            <label for="title">제목</label>
            <input type="text" id="title" name="title"  />
        </div>
        <div>
            <label for="name">작성자</label>
            <input type="text" id="name" name="name" />
        </div>
        <div>
            <label for="pwd">비밀번호</label>
            <input type="password" id="pwd" name="pwd" />
        </div>
        <div>
            <label for="email">이메일</label>
            <input type="text" id="email" name="email" />
        </div>
        <div>  
        	<label for="content">내용</label>        
 			<textarea id="content" name="content" rows="12" cols="40"></textarea>
        </div>
        <div>
            <label for="upfile">첨부파일</label>
            <input type="file" id="upfile" name="upfile" />(최대 2M)
        </div>
        <div class="center">
            <input type = "submit" value="등록"/>
            <input type = "Button" value="글목록" 
            	onclick
            ="location.href	='<c:url value="/reBoard/list.do" />'" />         
        </div>
    </fieldset>
</form>
</div>   

              
</body>
</html>
