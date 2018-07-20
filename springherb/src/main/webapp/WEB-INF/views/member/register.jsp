<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<script type="text/javascript" src="../js/member.js"></script>
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
			}else if(!validate_phoneno($('#hp2').val()) || 
					!validate_phoneno($('#hp3').val())){
				alert('휴대폰 번호는 숫자만 가능합니다.');
				$('#hp2').focus();
				bool= false;
			}else if($('#chkId').val()!='Y'){
				alert('중복확인을 먼저 하세요.');
				$('#btnChkId').focus();
				bool= false;			
			}
		}
		
		return bool;
	});
		
	/* $('#btnChkId').click(function(){
		//아이디 중복확인	
		var id=$('#userid').val();
		
		window.open("<c:url value='/member/checkUserid.do?userid="+id+"'/>", 
			'chkId', 
			'width=500,height=200,left=0,top=0,location=yes,resizable=yes');
	}); */
	
	
	$('#userid').keyup(function(){		
		if(validate_userid($(this).val()) && $(this).val().length>=3){
			$.ajax({
				url:"<c:url value='/member/ajaxCheckId.do'/>",
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
	action="<c:url value='/member/memberWrite.do'/>">
<fieldset>
	<legend>회원 가입</legend>
    <div>        
        <label for="name">성명</label>
        <input type="text" name="name" id="name" class="valid" style="ime-mode:active">
    </div>
    <div>
        <label for="userid">회원ID</label>
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
        <label for="zipcode">주소</label>
        <input type="text" name="zipcode" id="zipcode" ReadOnly  
        	title="우편번호" class="width_80">
        <input type="Button" value="우편번호 찾기" id="btnZipcode" title="새창열림"><br />
        <span class="sp1">&nbsp;</span>
        <input type="text" name="address" id="address" ReadOnly title="주소"  class="width_350"><br />
        <span class="sp1">&nbsp;</span>
        <input type="text" name="addressDetail" title="상세주소"  class="width_350">
    </div>
    <div>
        <label for="hp1">핸드폰</label>&nbsp;<select name="hp1" id="hp1" title="휴대폰 앞자리">
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
       	</select>
        -
        <input type="text" name="hp2" id="hp2" maxlength="4" title="휴대폰 가운데자리"
        	class="width_80">-
        <input type="text" name="hp3" id="hp3" maxlength="4" title="휴대폰 뒷자리"
        	class="width_80">
    </div>
    <div>
        <label for="email1">이메일 주소</label>
        <input type="text" name="email1"  id="email1" title="이메일주소 앞자리">@
        <select name="email2" id="email2"  title="이메일주소 뒷자리">
            <option value="naver.com">naver.com</option>
            <option value="hanmail.net">hanmail.net</option>
            <option value="nate.com">nate.com</option>
            <option value="gmail.com">gmail.com</option>
            <option value="etc">직접입력</option>
        </select>
        <input type="text" name="email3" id="email3" title="직접입력인 경우 이메일주소 뒷자리"
        	style="visibility:hidden">
    </div>
    <div class="center">
         <input type="submit" id="wr_submit" value="회원가입">
    </div>
</fieldset>

    <input type ="text" name="chkId" id="chkId">
        
</form>
</div>
</article>

<%@ include file="../inc/bottom.jsp"%>











