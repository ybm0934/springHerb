<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/adminTop.jsp" %>
<script type="text/javascript" src="<c:url value='/js/member.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#name').focus();
	
	$('#wr_submit').click(function(){			
		var bool=true;
		
		$('.valid').each(function(idx, item){
			if($(this).val().length<1){
				alert($(this).prev().text()+"을 입력하세요");
				$(this).focus();
				
				bool=false;  //submit() 이벤트 진행을 막는다
				return false;  //each() 탈출
			}
		});	
		
		if(bool){
			if(!validate_userid($('#userid').val())){
				alert('아이디는 알파벳이나 숫자 또는 특수기호인 _ 만 가능합니다.');
				$('#userid').focus();
				bool= false;
			}else if($('#pwd').val()!=$('#pwd2').val()){
				alert('비밀번호가 일치하지 않습니다.');
				$('#pwd2').focus();
				bool= false;
			}else if($('#chkId').val()!='Y'){
				alert('중복확인을 먼저 하세요.');
				$('#btnChkId').focus();
				bool= false;			
			}
		}
		
		return bool;
	});
	
	$('#userid').keyup(function(){		
		if(validate_userid($(this).val()) && $(this).val().length>=3){
			$.ajax({
				url:"<c:url value='/admin/manager/ajaxAdminCheckId.do'/>",
				type:"get",
				data:{userid:$(this).val()},
				success:function(res){
					//true, false
					if(res){  //true => 사용가능
						$('.message').html("사용가능한 아이디");
						$('.message').show();	
						$('#chkId').val('Y');
					}else{
						$('.message').html("이미 존재하는 아이디");
						$('.message').show();						
						$('#chkId').val('N');
					}
				},
				error:function(xhr, status, error){
					alert("error:"+error);
				}
			});
		}else{
			$('.message').html("아이디는 3자리 이상!");
			$('.message').show();
			$('#chkId').val('N');
		}
		
	});
	
});

	
</script>

<style type="text/css">
	.width_80{
		width:80px;
	}
	.width_350{
		width:350px;
	}	
	.message{
		color: red;
		font-weight: bold;
		display: none;
	}
</style>
<article>
<div class="divForm">
<form name="frm1" method="post" 
	action="<c:url value='/admin/manager/register.do'/>">
<fieldset>
	<legend>관리자 등록</legend>
    <div>        
        <label for="name">관리자명</label>
        <input type="text" name="name" id="name" class="valid" style="ime-mode:active">
    </div>
    <div>
        <label for="userid">관리자ID</label>
        <input type="text" name="userid" id="userid" 
        		style="ime-mode:inactive">&nbsp;
        <!-- <input type="button" value="중복확인" id="btnChkId"  title="새창열림"> -->
        <span class="message"></span>
    </div>
    <div>
        <label for="pwd">비밀번호</label>
        <input type="Password" name="pwd" id="pwd" class="valid">
    </div>
    <div>
        <label for="pwd2">비밀번호 확인</label>
        <input type="Password" name="pwd2" id="pwd2">
    </div>    
    <div>
        <label for="authCode">레벨</label>&nbsp;
        <select name="authCode" id="authCode" class="valid">
            <option value="">선택하세요</option>
            <!-- 반복 시작 -->
            <c:forEach var="map" items="${list }">
	            <option value="${map['AUTHCODE']}">${map['AUTHNAME']}</option>
            </c:forEach>
            <!-- 반복 끝 -->
       	</select>       
    </div>
    
    <div class="center">
         <input type="submit" id="wr_submit" value="등록">
    </div>
</fieldset>

    <input type ="text" name="chkId" id="chkId">
        
</form>
</div>
</article>

<%@ include file="../../inc/adminBottom.jsp" %>











