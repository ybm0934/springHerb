<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/adminTop.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		$('form[name=frmLogin]').submit(function(){
			var bool=true;
			
			$('.valid').each(function(idx, item){
				if($(this).val().length<1){
					alert($(this).prev().text()+"를 입력하세요");
					$(this).focus();
					bool=false;
					return false;
				}
			});
			
			return bool;
		});	
	});
</script>
<article class="simpleForm">
	<form name="frmLogin" method="post" 
		action="<c:url value='/admin/login/login.do'/>">
		<fieldset>
			<legend>관리자 로그인</legend>
			<div>
				<label for="userid" class="label">아이디</label>
				<input type="text" name="userid" class="valid" 
					value="${cookie.ck_admin_userid.value }">
			</div>
			<div>
				<label for="pwd" class="label">비밀번호</label>
				<input type="password" name="pwd" class="valid">
			</div>
			<div class="align_center">
				<input type="submit" value="로그인">
				<input type="checkbox" name="saveId" id="saveId" 
					<c:if test="${!empty cookie.ck_admin_userid}">
						checked="checked"
					</c:if>	>
				<label for="saveId">아이디 저장하기</label>
			</div>
		</fieldset>
	</form>

</article>

<%@ include file="../../inc/adminBottom.jsp" %>







